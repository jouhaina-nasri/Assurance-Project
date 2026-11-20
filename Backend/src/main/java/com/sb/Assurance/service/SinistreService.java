package com.sb.Assurance.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.StatutSinistre;
import com.sb.Assurance.entities.Voiture;
import com.sb.Assurance.reponses.TacheResponse;
import com.sb.Assurance.repository.SinistreRepository;
import com.sb.Assurance.repository.VoitureRepository;
import com.sb.Assurance.request.DecisionSinistreRequest;
import com.sb.Assurance.request.DeclarationSinistreRequest;
import com.sb.Assurance.workflow.SinistreWorkflowService;

/**
 * Déclaration des sinistres et pilotage du processus Camunda d'approbation.
 * Les groupes candidats Camunda (User, Admin) correspondent aux rôles ERole.
 */
@Service
public class SinistreService {

	private final SinistreRepository sinistreRepository;
	private final VoitureRepository voitureRepository;
	private final RuntimeService runtimeService;
	private final TaskService taskService;

	public SinistreService(SinistreRepository sinistreRepository, VoitureRepository voitureRepository,
			RuntimeService runtimeService, TaskService taskService) {
		this.sinistreRepository = sinistreRepository;
		this.voitureRepository = voitureRepository;
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	/** Enregistre le sinistre puis démarre le workflow (scoring ML, DMN, routage). */
	@Transactional
	public Sinistre declarer(DeclarationSinistreRequest req) {
		valider(req);
		Voiture voiture = voitureRepository.findBySerie(req.getMatricule().trim());
		if (voiture == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun véhicule avec ce matricule.");
		}

		Sinistre sinistre = new Sinistre();
		sinistre.setReference("SIN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		sinistre.setVoiture(voiture);
		sinistre.setDateSinistre(req.getDateSinistre());
		sinistre.setDateDeclaration(LocalDate.now());
		sinistre.setTypeSinistre(req.getTypeSinistre());
		sinistre.setMontantDeclare(req.getMontantDeclare());
		sinistre.setDescription(req.getDescription());
		sinistre.setAccidentNuit(req.isAccidentNuit());
		sinistre.setPresenceTemoins(req.isPresenceTemoins());
		sinistre.setConstatAmiable(req.isConstatAmiable());
		sinistre.setStatut(StatutSinistre.EN_EVALUATION);
		sinistre = sinistreRepository.save(sinistre);

		Map<String, Object> variables = new HashMap<>();
		variables.put("sinistreId", sinistre.getId());
		String processInstanceId = runtimeService
				.startProcessInstanceByKey(SinistreWorkflowService.PROCESS_KEY, sinistre.getReference(), variables)
				.getId();

		// Le processus s'exécute jusqu'à la première tâche humaine (ou jusqu'à la fin
		// en cas d'approbation automatique) : on relit l'état mis à jour par les délégués.
		Sinistre resultat = sinistreRepository.findById(sinistre.getId()).orElseThrow();
		resultat.setProcessInstanceId(processInstanceId);
		return sinistreRepository.save(resultat);
	}

	public List<Sinistre> lister() {
		return sinistreRepository.findAllByOrderByIdDesc();
	}

	public Sinistre trouver(Long id) {
		return sinistreRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinistre introuvable."));
	}

	/** Tâches de validation/expertise ouvertes aux groupes de l'utilisateur connecté. */
	public List<TacheResponse> listerTaches(Authentication auth) {
		List<String> groupes = groupes(auth);
		if (groupes.isEmpty()) {
			return List.of();
		}
		return taskService.createTaskQuery()
				.processDefinitionKey(SinistreWorkflowService.PROCESS_KEY)
				.taskCandidateGroupIn(groupes)
				.includeAssignedTasks()
				.orderByTaskCreateTime().desc()
				.list().stream()
				.map(t -> new TacheResponse(t.getId(), t.getName(), t.getTaskDefinitionKey(), t.getCreateTime(),
						sinistreRepository.findByProcessInstanceId(t.getProcessInstanceId()).orElse(null)))
				.collect(Collectors.toList());
	}

	@Transactional
	public Sinistre decider(String taskId, DecisionSinistreRequest req, Authentication auth) {
		Task task = taskService.createTaskQuery()
				.taskId(taskId)
				.taskCandidateGroupIn(groupes(auth))
				.includeAssignedTasks()
				.singleResult();
		if (task == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tâche introuvable ou non autorisée.");
		}
		Sinistre sinistre = sinistreRepository.findByProcessInstanceId(task.getProcessInstanceId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinistre introuvable."));

		Double montant = null;
		if (req.isApprouve()) {
			montant = req.getMontantIndemnise() != null ? req.getMontantIndemnise() : sinistre.getMontantDeclare();
			if (montant < 0 || montant > sinistre.getMontantDeclare()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Le montant indemnisé doit être compris entre 0 et le montant déclaré.");
			}
		} else if (req.getCommentaire() == null || req.getCommentaire().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un motif est obligatoire pour un rejet.");
		}

		Map<String, Object> variables = new HashMap<>();
		variables.put("approuve", req.isApprouve());
		variables.put("montantIndemnise", montant);
		variables.put("commentaire", req.getCommentaire());
		variables.put("decidePar", auth.getName());
		taskService.complete(task.getId(), variables);

		return sinistreRepository.findById(sinistre.getId()).orElseThrow();
	}

	private static void valider(DeclarationSinistreRequest req) {
		if (req.getMatricule() == null || req.getMatricule().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le matricule est obligatoire.");
		}
		if (req.getTypeSinistre() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le type de sinistre est obligatoire.");
		}
		if (req.getDateSinistre() == null || req.getDateSinistre().isAfter(LocalDate.now())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date du sinistre est invalide.");
		}
		if (req.getMontantDeclare() <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le montant déclaré doit être positif.");
		}
	}

	private static List<String> groupes(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
}

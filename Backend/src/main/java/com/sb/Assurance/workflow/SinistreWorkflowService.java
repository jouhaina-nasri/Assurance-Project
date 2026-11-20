package com.sb.Assurance.workflow;

import java.time.LocalDateTime;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.StatutSinistre;
import com.sb.Assurance.repository.SinistreRepository;

/**
 * Méthodes appelées par expression depuis le BPMN
 * (processes/approbation-sinistre.bpmn), par exemple
 * ${sinistreWorkflowService.cloturer(execution, true)}.
 */
@Component("sinistreWorkflowService")
public class SinistreWorkflowService {

	public static final String PROCESS_KEY = "approbation-sinistre";
	public static final String SYSTEME = "systeme";

	private final SinistreRepository sinistreRepository;
	private final String delaiEscalade;

	public SinistreWorkflowService(SinistreRepository sinistreRepository,
			@Value("${workflow.sinistre.delai-escalade:P2D}") String delaiEscalade) {
		this.sinistreRepository = sinistreRepository;
		this.delaiEscalade = delaiEscalade;
	}

	/** Durée ISO-8601 avant escalade d'une validation vers l'expertise (timer BPMN). */
	public String getDelaiEscalade() {
		return delaiEscalade;
	}

	static Sinistre charger(SinistreRepository repository, VariableScope scope) {
		Long id = (Long) scope.getVariable("sinistreId");
		return repository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Sinistre introuvable : " + id));
	}

	/** Après la table DMN : mémorise la décision de routage sur le sinistre. */
	public void enregistrerRoutage(DelegateExecution execution) {
		Sinistre sinistre = charger(sinistreRepository, execution);
		sinistre.setRoutage((String) execution.getVariable("routage"));
		sinistreRepository.save(sinistre);
	}

	/** Écouteur « create » des tâches humaines : EN_VALIDATION ou EN_EXPERTISE. */
	public void marquerStatut(DelegateTask task, String statut) {
		Sinistre sinistre = charger(sinistreRepository, task);
		sinistre.setStatut(StatutSinistre.valueOf(statut));
		sinistreRepository.save(sinistre);
	}

	/** Sinistre à faible risque et faible montant : indemnisé au montant déclaré. */
	public void approuverAutomatiquement(DelegateExecution execution) {
		Sinistre sinistre = charger(sinistreRepository, execution);
		sinistre.setStatut(StatutSinistre.APPROUVE);
		sinistre.setMontantIndemnise(sinistre.getMontantDeclare());
		sinistre.setCommentaireDecision("Approbation automatique : risque faible et montant sous le seuil.");
		sinistre.setDecidePar(SYSTEME);
		sinistre.setDateDecision(LocalDateTime.now());
		sinistreRepository.save(sinistre);
	}

	/** Fin du processus après une décision humaine (variables posées à la complétion de la tâche). */
	public void cloturer(DelegateExecution execution, boolean approuve) {
		Sinistre sinistre = charger(sinistreRepository, execution);
		sinistre.setStatut(approuve ? StatutSinistre.APPROUVE : StatutSinistre.REJETE);
		Object montant = execution.getVariable("montantIndemnise");
		sinistre.setMontantIndemnise(approuve && montant != null ? ((Number) montant).doubleValue() : null);
		sinistre.setCommentaireDecision((String) execution.getVariable("commentaire"));
		sinistre.setDecidePar((String) execution.getVariable("decidePar"));
		sinistre.setDateDecision(LocalDateTime.now());
		sinistreRepository.save(sinistre);
	}
}

package com.sb.Assurance.workflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import com.sb.Assurance.entities.Client;
import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.StatutSinistre;
import com.sb.Assurance.entities.TypeSinistre;
import com.sb.Assurance.entities.Typevoiture;
import com.sb.Assurance.entities.Voiture;
import com.sb.Assurance.ml.PredictionRisque;
import com.sb.Assurance.ml.RisqueSinistreClient;
import com.sb.Assurance.reponses.TacheResponse;
import com.sb.Assurance.repository.ClientRepository;
import com.sb.Assurance.repository.VoitureRepository;
import com.sb.Assurance.request.DecisionSinistreRequest;
import com.sb.Assurance.request.DeclarationSinistreRequest;
import com.sb.Assurance.service.SinistreService;

@SpringBootTest
class ApprobationSinistreWorkflowTest {

	private static final Authentication AGENT = new UsernamePasswordAuthenticationToken("agent1", null,
			AuthorityUtils.createAuthorityList("User"));
	private static final Authentication ADMIN = new UsernamePasswordAuthenticationToken("expert1", null,
			AuthorityUtils.createAuthorityList("Admin"));

	@Autowired SinistreService sinistreService;
	@Autowired ClientRepository clientRepository;
	@Autowired VoitureRepository voitureRepository;
	@Autowired RuntimeService runtimeService;
	@Autowired ManagementService managementService;

	@MockBean RisqueSinistreClient risqueClient;

	private String matricule;

	@BeforeEach
	void creerVehicule() {
		Client client = new Client();
		client.setCin(System.nanoTime());
		client.setNom("Ben Salah");
		client.setPrenom("Amine");
		client.setClasse(3);
		client.setActif(1);
		client = clientRepository.save(client);

		Voiture voiture = new Voiture();
		matricule = "TU-" + System.nanoTime();
		voiture.setMatricule(matricule);
		voiture.setPrix(40);
		voiture.setType(Typevoiture.privee);
		voiture.setNbplace(5);
		voiture.setCv(6);
		voiture.setMf(12);
		voiture.setClient(client);
		voitureRepository.save(voiture);
	}

	private DeclarationSinistreRequest declaration(double montant) {
		DeclarationSinistreRequest req = new DeclarationSinistreRequest();
		req.setMatricule(matricule);
		req.setDateSinistre(LocalDate.now().minusDays(1));
		req.setTypeSinistre(TypeSinistre.collision);
		req.setMontantDeclare(montant);
		req.setPresenceTemoins(true);
		return req;
	}

	private void modeleRepond(double score, String niveau) {
		when(risqueClient.evaluer(any(Sinistre.class), anyLong()))
				.thenReturn(new PredictionRisque(score, niveau, "risque-sinistre-test"));
	}

	private TacheResponse tacheDe(Sinistre s, Authentication auth) {
		List<TacheResponse> taches = sinistreService.listerTaches(auth);
		return taches.stream().filter(t -> t.getSinistre().getId().equals(s.getId())).findFirst().orElse(null);
	}

	@Test
	void risqueFaibleEtPetitMontant_approuveAutomatiquement() {
		modeleRepond(0.04, "FAIBLE");

		Sinistre s = sinistreService.declarer(declaration(1200));

		assertThat(s.getStatut()).isEqualTo(StatutSinistre.APPROUVE);
		assertThat(s.getRoutage()).isEqualTo("AUTO_APPROBATION");
		assertThat(s.getMontantIndemnise()).isEqualTo(1200);
		assertThat(s.getScoreRisque()).isEqualTo(0.04);
		assertThat(runtimeService.createProcessInstanceQuery().processInstanceId(s.getProcessInstanceId()).count())
				.isZero();
	}

	@Test
	void risqueMoyen_validationParAgent_puisApprobationPartielle() {
		modeleRepond(0.35, "MOYEN");

		Sinistre s = sinistreService.declarer(declaration(5000));
		assertThat(s.getStatut()).isEqualTo(StatutSinistre.EN_VALIDATION);
		assertThat(tacheDe(s, ADMIN)).isNull();

		TacheResponse tache = tacheDe(s, AGENT);
		assertThat(tache.getCle()).isEqualTo("valider-sinistre");

		DecisionSinistreRequest decision = new DecisionSinistreRequest();
		decision.setApprouve(true);
		decision.setMontantIndemnise(4000.0);
		Sinistre fin = sinistreService.decider(tache.getId(), decision, AGENT);

		assertThat(fin.getStatut()).isEqualTo(StatutSinistre.APPROUVE);
		assertThat(fin.getMontantIndemnise()).isEqualTo(4000.0);
		assertThat(fin.getDecidePar()).isEqualTo("agent1");
	}

	@Test
	void risqueEleve_expertiseParAdmin_puisRejet() {
		modeleRepond(0.81, "ELEVE");

		Sinistre s = sinistreService.declarer(declaration(9000));
		assertThat(s.getStatut()).isEqualTo(StatutSinistre.EN_EXPERTISE);
		assertThat(tacheDe(s, AGENT)).isNull();

		TacheResponse tache = tacheDe(s, ADMIN);
		DecisionSinistreRequest sansMotif = new DecisionSinistreRequest();
		sansMotif.setApprouve(false);
		assertThatThrownBy(() -> sinistreService.decider(tache.getId(), sansMotif, ADMIN))
				.isInstanceOf(ResponseStatusException.class);

		DecisionSinistreRequest rejet = new DecisionSinistreRequest();
		rejet.setApprouve(false);
		rejet.setCommentaire("Incohérences entre le constat et les photos.");
		Sinistre fin = sinistreService.decider(tache.getId(), rejet, ADMIN);

		assertThat(fin.getStatut()).isEqualTo(StatutSinistre.REJETE);
		assertThat(fin.getMontantIndemnise()).isNull();
	}

	@Test
	void serviceMlIndisponible_validationManuelle() {
		when(risqueClient.evaluer(any(Sinistre.class), anyLong()))
				.thenThrow(new ResourceAccessException("Connection refused"));

		Sinistre s = sinistreService.declarer(declaration(800));

		assertThat(s.getNiveauRisque()).isEqualTo("INDISPONIBLE");
		assertThat(s.getScoreRisque()).isNull();
		assertThat(s.getRoutage()).isEqualTo("VALIDATION_AGENT");
		assertThat(s.getStatut()).isEqualTo(StatutSinistre.EN_VALIDATION);
	}

	@Test
	void validationSansReponse_escaladeVersExpertise() {
		modeleRepond(0.30, "MOYEN");
		Sinistre s = sinistreService.declarer(declaration(4000));

		Job timer = managementService.createJobQuery().processInstanceId(s.getProcessInstanceId()).timers()
				.singleResult();
		managementService.executeJob(timer.getId());

		assertThat(tacheDe(s, AGENT)).isNull();
		assertThat(tacheDe(s, ADMIN).getCle()).isEqualTo("expertiser-sinistre");
		assertThat(sinistreService.trouver(s.getId()).getStatut()).isEqualTo(StatutSinistre.EN_EXPERTISE);
	}

	@Test
	void montantSuperieurAuDeclare_refuse() {
		modeleRepond(0.30, "MOYEN");
		Sinistre s = sinistreService.declarer(declaration(2000));
		TacheResponse tache = tacheDe(s, AGENT);

		DecisionSinistreRequest decision = new DecisionSinistreRequest();
		decision.setApprouve(true);
		decision.setMontantIndemnise(2500.0);

		assertThatThrownBy(() -> sinistreService.decider(tache.getId(), decision, AGENT))
				.isInstanceOf(ResponseStatusException.class);
	}
}

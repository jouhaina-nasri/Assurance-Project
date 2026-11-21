package com.sb.Assurance.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.ml.PredictionRisque;
import com.sb.Assurance.ml.RisqueSinistreClient;
import com.sb.Assurance.repository.SinistreRepository;

/**
 * Tâche de service BPMN « Évaluer le risque » : interroge le modèle ML et
 * enregistre le score sur le sinistre. Si le service ML est indisponible, le
 * sinistre est marqué INDISPONIBLE et la table DMN l'envoie en validation manuelle.
 */
@Component("evaluationRisqueDelegate")
public class EvaluationRisqueDelegate implements JavaDelegate {

	public static final String NIVEAU_INDISPONIBLE = "INDISPONIBLE";

	private static final Logger log = LoggerFactory.getLogger(EvaluationRisqueDelegate.class);

	private final SinistreRepository sinistreRepository;
	private final RisqueSinistreClient risqueClient;

	public EvaluationRisqueDelegate(SinistreRepository sinistreRepository, RisqueSinistreClient risqueClient) {
		this.sinistreRepository = sinistreRepository;
		this.risqueClient = risqueClient;
	}

	@Override
	public void execute(DelegateExecution execution) {
		Sinistre sinistre = SinistreWorkflowService.charger(sinistreRepository, execution);
		long anterieurs = sinistreRepository.countByVoitureAndDateSinistreBefore(sinistre.getVoiture(),
				sinistre.getDateSinistre());

		try {
			PredictionRisque prediction = risqueClient.evaluer(sinistre, anterieurs);
			sinistre.setScoreRisque(prediction.getScoreRisque());
			sinistre.setNiveauRisque(prediction.getNiveauRisque());
			sinistre.setVersionModele(prediction.getVersionModele());
		} catch (RestClientException e) {
			log.warn("Service ML indisponible pour le sinistre {} : {}", sinistre.getReference(), e.getMessage());
			sinistre.setScoreRisque(null);
			sinistre.setNiveauRisque(NIVEAU_INDISPONIBLE);
			sinistre.setVersionModele(null);
		}
		sinistreRepository.save(sinistre);

		execution.setVariable("niveauRisque", sinistre.getNiveauRisque());
		execution.setVariable("scoreRisque", sinistre.getScoreRisque());
		execution.setVariable("montantDeclare", sinistre.getMontantDeclare());
	}
}

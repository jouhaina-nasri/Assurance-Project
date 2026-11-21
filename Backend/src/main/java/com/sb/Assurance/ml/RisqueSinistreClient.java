package com.sb.Assurance.ml;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sb.Assurance.entities.Client;
import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.Voiture;

/**
 * Appelle le service Python de scoring du risque.
 * Les noms de champs suivent le schéma de ml-service/app/main.py.
 */
@Component
public class RisqueSinistreClient {

	private final RestTemplate restTemplate;
	private final String baseUrl;

	public RisqueSinistreClient(RestTemplateBuilder builder,
			@Value("${ml.service.url:http://localhost:8000}") String baseUrl,
			@Value("${ml.service.timeout-ms:3000}") long timeoutMs) {
		this.restTemplate = builder
				.setConnectTimeout(Duration.ofMillis(timeoutMs))
				.setReadTimeout(Duration.ofMillis(timeoutMs))
				.build();
		this.baseUrl = baseUrl;
	}

	/**
	 * @throws RestClientException si le service est injoignable ou répond en erreur
	 */
	public PredictionRisque evaluer(Sinistre sinistre, long nbSinistresAnterieurs) {
		PredictionRisque prediction = restTemplate.postForObject(baseUrl + "/predict",
				construireRequete(sinistre, nbSinistresAnterieurs), PredictionRisque.class);
		if (prediction == null) {
			throw new RestClientException("Réponse vide du service ML");
		}
		return prediction;
	}

	static Map<String, Object> construireRequete(Sinistre sinistre, long nbSinistresAnterieurs) {
		Voiture voiture = sinistre.getVoiture();
		Client client = voiture.getClient();
		long delai = Math.max(0, ChronoUnit.DAYS.between(sinistre.getDateSinistre(), sinistre.getDateDeclaration()));

		Map<String, Object> body = new HashMap<>();
		body.put("type_vehicule", voiture.getType().name());
		body.put("type_sinistre", sinistre.getTypeSinistre().name());
		body.put("puissance_cv", voiture.getCv());
		body.put("valeur_vehicule", voiture.getPrix());
		body.put("nb_places", voiture.getNbplace());
		body.put("classe_bonus_malus", client != null ? client.getClasse() : 1);
		body.put("montant_declare", sinistre.getMontantDeclare());
		body.put("delai_declaration_jours", delai);
		body.put("nb_sinistres_anterieurs", nbSinistresAnterieurs);
		body.put("accident_nuit", sinistre.isAccidentNuit());
		body.put("presence_temoins", sinistre.isPresenceTemoins());
		body.put("constat_amiable", sinistre.isConstatAmiable());
		return body;
	}
}

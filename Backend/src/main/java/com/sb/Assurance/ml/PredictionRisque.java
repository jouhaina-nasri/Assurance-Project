package com.sb.Assurance.ml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Réponse du service ML (POST /predict). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionRisque {

	@JsonProperty("score_risque")
	private double scoreRisque;

	@JsonProperty("niveau_risque")
	private String niveauRisque;

	@JsonProperty("version_modele")
	private String versionModele;
}

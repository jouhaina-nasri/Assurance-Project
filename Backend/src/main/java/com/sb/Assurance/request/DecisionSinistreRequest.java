package com.sb.Assurance.request;

import lombok.Data;

@Data
public class DecisionSinistreRequest {

	private boolean approuve;

	/** Montant accordé en dinars ; par défaut le montant déclaré si le sinistre est approuvé. */
	private Double montantIndemnise;

	private String commentaire;
}

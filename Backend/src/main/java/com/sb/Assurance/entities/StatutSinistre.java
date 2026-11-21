package com.sb.Assurance.entities;

/** Cycle de vie d'un sinistre dans le workflow d'approbation. */
public enum StatutSinistre {

	EN_EVALUATION,
	EN_VALIDATION,
	EN_EXPERTISE,
	APPROUVE,
	REJETE;
}

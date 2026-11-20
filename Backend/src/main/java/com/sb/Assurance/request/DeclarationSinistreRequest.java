package com.sb.Assurance.request;

import java.time.LocalDate;

import com.sb.Assurance.entities.TypeSinistre;

import lombok.Data;

/** Données saisies par l'agent ; validées dans SinistreService. */
@Data
public class DeclarationSinistreRequest {

	private String matricule;

	private LocalDate dateSinistre;

	private TypeSinistre typeSinistre;

	private double montantDeclare;

	private String description;

	private boolean accidentNuit;

	private boolean presenceTemoins;

	private boolean constatAmiable;
}

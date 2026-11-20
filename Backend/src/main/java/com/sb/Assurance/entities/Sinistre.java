package com.sb.Assurance.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sinistre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "reference", nullable = false, unique = true)
	private String reference;

	@ManyToOne(optional = false)
	private Voiture voiture;

	@Column(name = "date_sinistre", nullable = false)
	private LocalDate dateSinistre;

	@Column(name = "date_declaration", nullable = false)
	private LocalDate dateDeclaration;

	@Enumerated(EnumType.STRING)
	@Column(name = "type_sinistre", nullable = false)
	private TypeSinistre typeSinistre;

	/** Montant déclaré en dinars. */
	@Column(name = "montant_declare", nullable = false)
	private double montantDeclare;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "accident_nuit", nullable = false)
	private boolean accidentNuit;

	@Column(name = "presence_temoins", nullable = false)
	private boolean presenceTemoins;

	@Column(name = "constat_amiable", nullable = false)
	private boolean constatAmiable;

	@Enumerated(EnumType.STRING)
	@Column(name = "statut", nullable = false)
	private StatutSinistre statut;

	/** Probabilité calibrée renvoyée par le modèle ML (null si le service était indisponible). */
	@Column(name = "score_risque")
	private Double scoreRisque;

	/** FAIBLE, MOYEN, ELEVE ou INDISPONIBLE. */
	@Column(name = "niveau_risque")
	private String niveauRisque;

	@Column(name = "version_modele")
	private String versionModele;

	/** Résultat de la table de décision DMN : AUTO_APPROBATION, VALIDATION_AGENT ou EXPERTISE. */
	@Column(name = "routage")
	private String routage;

	@Column(name = "montant_indemnise")
	private Double montantIndemnise;

	@Column(name = "commentaire_decision", length = 1000)
	private String commentaireDecision;

	@Column(name = "decide_par")
	private String decidePar;

	@Column(name = "date_decision")
	private LocalDateTime dateDecision;

	@Column(name = "process_instance_id")
	private String processInstanceId;
}

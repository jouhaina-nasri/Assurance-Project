package com.sb.Assurance.reponses;

import java.util.Date;

import com.sb.Assurance.entities.Sinistre;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Tâche humaine Camunda (validation ou expertise) avec le sinistre associé. */
@Data
@AllArgsConstructor
public class TacheResponse {

	private String id;
	private String nom;
	private String cle;
	private Date creation;
	private Sinistre sinistre;
}

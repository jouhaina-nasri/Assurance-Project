package com.sb.Assurance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Voiture implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name="Id")
	private Long id;
	@Column(name="matricule",nullable = false)
	private String matricule;
	@Column(name="prix",nullable = false)
	private double prix;
	@Column(name="type",nullable = false)
	private Typevoiture type;
	@Column(name="nbplace",nullable = false)
	private int nbplace;
	@Column(name="bgl",nullable = false)
	private int bgl;
	@Column(name="assist",nullable = false)
	private int assist;
	@Column(name="cv",nullable = false)
	private int cv;
	@Column(name="MF",nullable = false)
	private int mf;
	@ManyToOne
	private Client client;

}

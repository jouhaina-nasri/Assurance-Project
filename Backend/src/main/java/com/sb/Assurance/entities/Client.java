package com.sb.Assurance.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name="Id")
	private Long id;
	@Column(name="cin",nullable = false)
	private Long cin;
	@Column(name="nom",nullable = false)
	private String nom;
	@Column(name="prenom",nullable = false)
	private String prenom;
	@Column(name="classe",nullable = false)
	private int classe;
	@Column(name="actif",nullable = false)
	private int actif;
	

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="client")
	private List<Voiture> voiture;
	
    @ManyToOne
	private Agence agence;

}

package com.sb.Assurance.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agence implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name="Id")
	private Long id;
	
	@Column(name="code",nullable = false)
	private Long code;
	
	@Column(name="local",nullable = false)
	private String local;
	
	@Column(name="nom",nullable = false)
	private String nom;
	
	@Column(name="prenom",nullable = false)
	private String prenom;
	
	@Column(name="tel",nullable = false)
	private String tel;
	
	@Column(name="email",nullable = false)
	private String email;
	
	@Column(name="actif",nullable = false)
	private int actif;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="agence")
	private List<Agent> agent;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="agence")
	private List<Client> client;
}

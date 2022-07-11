package com.sb.Assurance.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agent implements Serializable {
	

	public Agent(Long cin, String nom, String prenom, String username, String password) {
		super();
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.password = password;
	}

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
	@Column(name="username",nullable = false)
	private String username;
	@Column(name="password",nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "agent_roles", joinColumns = @JoinColumn(name = "agent_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


	@Column(name="actif",nullable = false)
	private int actif;
	
	@ManyToOne
	private Agence agence;
}

package com.sb.Assurance.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

public class SignupRequest {
    @NotBlank
    private String username;
 
    @NotBlank
    private String nom;
    
    @NotBlank
    private Long cin;
    
    @NotBlank
    private String prenom;

    
    private Set<String> role;
    
    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getCin() {
		return cin;
	}

	public void setCin(Long cin) {
		this.cin = cin;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@NotBlank
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
}


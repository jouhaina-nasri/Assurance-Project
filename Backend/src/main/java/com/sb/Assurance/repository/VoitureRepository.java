package com.sb.Assurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sb.Assurance.entities.Voiture;

public interface VoitureRepository extends JpaRepository<Voiture, Long> 
{
	@Query("select u from Voiture u where u.matricule = ?1 ")
	Voiture findBySerie(String serie);
	
	@Query("select u from Voiture u where u.id = ?1 ")
	Voiture findById(int id);
	
}
package com.sb.Assurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sb.Assurance.entities.Taxi;

public interface TaxiRepository extends JpaRepository<Taxi, Long> 
{

	@Query("select u.taux from Classe u where u.classe = ?1 ")
	double findByClasse(int classe);
	
	@Query("select u from Taxi u where u.MF = ?1 ")
	Taxi findByMF(int MF);

}
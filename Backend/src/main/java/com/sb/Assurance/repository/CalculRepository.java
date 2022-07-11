package com.sb.Assurance.repository;

import com.sb.Assurance.entities.Assurance;
import com.sb.Assurance.entities.Chevaux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculRepository extends JpaRepository<Assurance, Long> 
{
	@Query("select u from Chevaux u where u.CV = ?1 ")
	Chevaux findByCV(int CV);
	@Query("select u.taux from Classe u where u.classe = ?1 ")
	double findByClasse(int classe);
	@Query("select u from Assurance u where u.MF = ?1 ")
	Assurance findByMF(int MF);
}

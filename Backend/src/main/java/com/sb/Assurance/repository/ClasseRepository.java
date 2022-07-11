package com.sb.Assurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sb.Assurance.entities.Classe;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> 
{
	@Query("select u.taux from Classe u where u.classe = ?1 ")
	double findByClasse(int classe);
	
	@Query("select u from Classe u where u.classe = ?1 ")
	Classe findByC(int classe);
}
package com.sb.Assurance.repository;


import com.sb.Assurance.entities.Commerciale;
import com.sb.Assurance.entities.Chevaux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CalculComRepository extends JpaRepository<Commerciale, Long> 
{
	@Query("select u from Chevaux u where u.CV = ?1 ")
	Chevaux findByCV(int CV);
	@Query("select u.taux from Classe u where u.classe = ?1 ")
	double findByClasse(int classe);
	@Query("select u from Commerciale u where u.MF = ?1 ")
	Commerciale findByMF(int MF);

}
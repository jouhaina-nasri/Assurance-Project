package com.sb.Assurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sb.Assurance.entities.Chevaux;

@Repository
public interface ChevauxRepository extends JpaRepository<Chevaux, Long> 
{
	@Query("select u from Chevaux u where u.CV = ?1 ")
	Chevaux findByCV(int CV);
}
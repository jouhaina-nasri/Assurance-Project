package com.sb.Assurance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sb.Assurance.entities.Agence;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long>{ 


	@Query("select u from Agence u where u.code = ?1 ")
	Agence findByCode(Long cin);
	
	@Query("select u from Agence u where u.actif = '1' ")
	List<Agence> findAllDebloquer();
	
	@Query("select u from Agence u where u.actif = '0' ")
	List<Agence> findAllBloquer();
	


}

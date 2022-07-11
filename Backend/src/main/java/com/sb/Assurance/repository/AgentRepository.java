package com.sb.Assurance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sb.Assurance.entities.Agent;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>{ 

	@Query("select u from Agent u where u.cin = ?1 ")
	Agent findByCin(Long cin);

	@Query("select u from Agent u where u.actif = '1' and u.agence!=null ")
	List<Agent> findAllDebloquer();
	
	@Query("select u from Agent u where u.actif = '0' ")
	List<Agent> findAllBloquer();
	
	Optional<Agent> findByUsername(String username);
	Boolean existsByUsername(String username);
	
}

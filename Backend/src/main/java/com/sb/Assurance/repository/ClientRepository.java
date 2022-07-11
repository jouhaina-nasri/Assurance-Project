package com.sb.Assurance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sb.Assurance.entities.Client;


public interface ClientRepository  extends JpaRepository<Client, Long> 
{
	@Query("select u from Client u where u.cin = ?1 ")
	Client findByCin(Long cin);
	
	@Query("select u from Client u where u.actif = '1' ")
	List<Client> findAllDebloquer();
	
	@Query("select u from Client u where u.actif = '0' ")
	List<Client> findAllBloquer();
	
	
	Optional<Client> findById(Long id);
	
}
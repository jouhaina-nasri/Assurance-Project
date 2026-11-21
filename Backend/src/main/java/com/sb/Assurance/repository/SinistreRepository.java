package com.sb.Assurance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.entities.Voiture;

public interface SinistreRepository extends JpaRepository<Sinistre, Long> {

	List<Sinistre> findAllByOrderByIdDesc();

	Optional<Sinistre> findByProcessInstanceId(String processInstanceId);

	/** Sinistres du même véhicule survenus avant la date donnée (historique). */
	long countByVoitureAndDateSinistreBefore(Voiture voiture, LocalDate date);
}

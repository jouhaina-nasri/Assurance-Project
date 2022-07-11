package com.sb.Assurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sb.Assurance.entities.Voiture;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.service.VoitureServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/voiture")
public class VoitureController {
	
	@Autowired
	  private VoitureServiceImpl voitureService;
	
	@PreAuthorize("hasAuthority('User')")
	 @GetMapping
	  public List<Voiture> findAll() {
	      return voitureService.findAll();
	  }

	@PreAuthorize("hasAuthority('User')")
	  @PostMapping("/{code}")
	  public MessageResponse save(@RequestBody Voiture voiture,@PathVariable("code") Long idClient) {
	      return voitureService.save(voiture,idClient);
	  }

	@PreAuthorize("hasAuthority('User')")
	  @PutMapping("/{idVoiture}/{idClient}")
	  public MessageResponse update(@RequestBody Voiture voiture,@PathVariable("idVoiture") Long idVoiture,@PathVariable("idClient") Long idClient) {
	      return voitureService.update(voiture,idVoiture,idClient);
	  }

	@PreAuthorize("hasAuthority('User')")
	 @GetMapping("/{code}")
	  public Voiture findByMF(@PathVariable("code") Long id) {
	      return voitureService.findById(id);
	  }
	 
	@PreAuthorize("hasAuthority('User')")
	 @DeleteMapping("/{id}")
		public MessageResponse delete(@PathVariable Long id) {
			return voitureService.delete(id);
		}

	@PreAuthorize("hasAuthority('User')")
	 @GetMapping("/calcul/{id}")
		public double calcul(@PathVariable String id) {
			return voitureService.calcul(id);
		}
}

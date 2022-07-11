package com.sb.Assurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.service.AgenceServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/agence")
public class AgenceController {
	@Autowired
	  private AgenceServiceImpl agenceService;
	
    @PreAuthorize("hasAuthority('Admin')")
	@PostMapping

	  public MessageResponse save(@RequestBody Agence agence) {
	      return agenceService.save(agence);
	  }

    @PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/{code}")
	  public MessageResponse update(@PathVariable("code") Long id,@RequestBody Agence agence) {
	      return agenceService.update(id,agence);
	  }

    @PreAuthorize("hasAuthority('Admin')")
	 @PutMapping("/fermer/{id}")
	  public MessageResponse Bloquer(@PathVariable Long id) {
			return agenceService.Bloquer(id);
		}
	 
    @PreAuthorize("hasAuthority('Admin')")
	 @PutMapping("/ouvrir/{id}")
	  public MessageResponse Debloquer(@PathVariable Long id) {
			return agenceService.Debloquer(id);
		}

    @PreAuthorize("hasAuthority('Admin')")
	 @GetMapping("/bloquer")
	  public List<Agence> findAllClosed() {
	      return agenceService.findAllClosed();
	  }
	
	 @GetMapping
	  public List<Agence> findAllOpen() {
	      return agenceService.findAllOpen();
	  }


	 @GetMapping("/{code}")
	  public Agence findByID(@PathVariable("code") Long id) {
	      return agenceService.findById(id);
	  }


}

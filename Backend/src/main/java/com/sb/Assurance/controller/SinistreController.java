package com.sb.Assurance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sb.Assurance.entities.Sinistre;
import com.sb.Assurance.reponses.TacheResponse;
import com.sb.Assurance.request.DecisionSinistreRequest;
import com.sb.Assurance.request.DeclarationSinistreRequest;
import com.sb.Assurance.service.SinistreService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sinistre")
public class SinistreController {

	private final SinistreService sinistreService;

	public SinistreController(SinistreService sinistreService) {
		this.sinistreService = sinistreService;
	}

	@PreAuthorize("hasAuthority('User')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Sinistre declarer(@RequestBody DeclarationSinistreRequest request) {
		return sinistreService.declarer(request);
	}

	@PreAuthorize("hasAnyAuthority('User','Admin')")
	@GetMapping
	public List<Sinistre> lister() {
		return sinistreService.lister();
	}

	@PreAuthorize("hasAnyAuthority('User','Admin')")
	@GetMapping("/{id}")
	public Sinistre trouver(@PathVariable Long id) {
		return sinistreService.trouver(id);
	}

	/** Tâches Camunda en attente pour les groupes de l'utilisateur (User : validation, Admin : expertise). */
	@PreAuthorize("hasAnyAuthority('User','Admin')")
	@GetMapping("/taches")
	public List<TacheResponse> taches(Authentication auth) {
		return sinistreService.listerTaches(auth);
	}

	@PreAuthorize("hasAnyAuthority('User','Admin')")
	@PostMapping("/taches/{taskId}/decision")
	public Sinistre decider(@PathVariable String taskId, @RequestBody DecisionSinistreRequest request,
			Authentication auth) {
		return sinistreService.decider(taskId, request, auth);
	}
}

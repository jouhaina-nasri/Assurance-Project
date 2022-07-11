package com.sb.Assurance.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.entities.Agent;
import com.sb.Assurance.entities.ERole;
import com.sb.Assurance.entities.Role;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.AgenceRepository;
import com.sb.Assurance.repository.AgentRepository;
import com.sb.Assurance.repository.RoleRepository;
import com.sb.Assurance.request.SignupRequest;
import com.sb.Assurance.security.jwt.JwtUtils;
import com.sb.Assurance.service.AgentServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/agent")
public class AgentController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AgentRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AgenceRepository agenceRepository;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private AgentServiceImpl agentService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/{idagence}")
	public ResponseEntity<?> registerUser(@PathVariable("idagence") Long id,@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse(false,"Echec !","Error: Username is already taken!"));

		}
		
		// Create new user's account
		Agent user = new Agent(signUpRequest.getCin(),signUpRequest.getNom(),signUpRequest.getPrenom(),
				signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName(ERole.User)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		Agence a = agenceRepository.findById(id).orElse(null);
		user.setActif(1);
		user.setAgence(a);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse(true,"Succès", "User registered successfully!"));
	}

    @PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/{code}/{idagence}")
	  public MessageResponse update(@PathVariable("code") Long id,@PathVariable("idagence") Long idagence,@RequestBody Agent agent) {
	      return agentService.update(id,idagence,agent);
	  }

    @PreAuthorize("hasAuthority('Admin')")
	 @PutMapping("/bloquer/{id}")
	  public MessageResponse Bloquer(@PathVariable Long id) {
			return agentService.Bloquer(id);
		}
	 
    @PreAuthorize("hasAuthority('Admin')")
	 @PutMapping("/debloquer/{id}")
	  public MessageResponse Debloquer(@PathVariable Long id) {
			return agentService.Debloquer(id);
		}

    @PreAuthorize("hasAuthority('Admin')")
	 @GetMapping("/bloquer")
	  public List<Agent> findAllBloquer() {
	      return agentService.findAllBloquer();
	  }
	 
	 @GetMapping
	  public List<Agent> findAllDebloquer() {
	      return agentService.findAllDebloquer();
	  }

	 @GetMapping("/{code}")
	  public Agent findByID(@PathVariable("code") Long id) {
	      return agentService.findById(id);
	  }


}

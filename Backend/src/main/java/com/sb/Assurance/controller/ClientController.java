package com.sb.Assurance.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sb.Assurance.entities.Client;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.service.ClientServiceImpl;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	  private ClientServiceImpl clientService;
	
	 @PreAuthorize("hasAuthority('User')")
	 @GetMapping("/bloquer")
	  public List<Client> findAllBloquer() {
	      return clientService.findAllBloquer();
	  }
	 
	 @GetMapping
	  public List<Client> findAllDebloquer() {
	      return clientService.findAllDebloquer();
	  }

	 @PreAuthorize("hasAuthority('User')")
	  @PostMapping("/{code}")
	  public MessageResponse save(@PathVariable("code") Long id,@RequestBody Client client) {
	      return clientService.save(client,id);
	  }

	 @PreAuthorize("hasAuthority('User')")
	  @PutMapping("/{code}/{idagent}")
	  public MessageResponse update(@PathVariable("code") Long id,@PathVariable("idagent") Long idagent,@RequestBody Client client) {
	      return clientService.update(id,idagent,client);
	  }

	 @PreAuthorize("hasAuthority('User')")
	 @GetMapping("/{code}")
	  public Client findById(@PathVariable("code") Long id) {
	      return clientService.findById(id);
	  }

	 @PreAuthorize("hasAuthority('User')")
	 @GetMapping("/cin/{code}")
	  public Client findByCin(@PathVariable("code") Long id) {
	      return clientService.findByCin(id);
	  }

	 @PreAuthorize("hasAuthority('User')")
	 @PutMapping("/bloquer/{id}")
		public MessageResponse Bloquer(@PathVariable Long id) {
			return clientService.Bloquer(id);
		}
	 
	 @PreAuthorize("hasAuthority('User')")
	 @PutMapping("/debloquer/{id}")
		public MessageResponse Debloquer(@PathVariable Long id) {
			return clientService.Debloquer(id);
		}
}

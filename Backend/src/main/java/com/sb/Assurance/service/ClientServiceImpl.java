package com.sb.Assurance.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.entities.Client;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.AgenceRepository;
import com.sb.Assurance.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	AgenceRepository agenceRepository;
	
	@Transactional
    @Override
	public MessageResponse save(Client classe,Long id) {
		Client existe = clientRepository.findByCin(classe.getCin());
		if (existe!=null) {
			return new MessageResponse(false, "Echec !", "Cette session de formation existe déja !");
		}
		Agence a = agenceRepository.findById(id).orElse(null);
		classe.setAgence(a);
		classe.setActif(1);
		clientRepository.save(classe);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}

	@Transactional
    @Override
	public MessageResponse update(Long id,Long idagence, Client classe) {
		boolean existe = clientRepository.existsById(id);
		if (!existe) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		Agence a = agenceRepository.findById(idagence).orElse(null);
		classe.setAgence(a);
		classe.setId(id);
		classe.setActif(1);
		clientRepository.save(classe);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}

	@Transactional
    @Override
	public List<Client> findAllBloquer() {
		return clientRepository.findAllBloquer();
	}
	
	@Transactional
    @Override
	public List<Client> findAllDebloquer() {
		return clientRepository.findAllDebloquer();
	}

	@Transactional
    @Override
	public Client findById(Long id) {
		Client structure = clientRepository.findById(id).orElse(null);
		return structure;
	}

	@Override
	public MessageResponse Bloquer(Long id) {
		Client client = clientRepository.findById(id).orElse(null);
		if (client==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		client.setActif(0);
		client.setId(id);
		clientRepository.save(client);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}
	
	@Override
	public MessageResponse Debloquer(Long id) {
		Client client = clientRepository.findById(id).orElse(null);
		if (client==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		client.setActif(1);
		client.setId(id);
		clientRepository.save(client);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}

	@Override
	public Client findByCin(Long id) {
		Client structure = clientRepository.findByCin(id);
		return structure;
	}

}

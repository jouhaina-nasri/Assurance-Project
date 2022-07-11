package com.sb.Assurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.AgenceRepository;

@Service
public class AgenceServiceImpl implements AgenceService{

	@Autowired
	AgenceRepository agenceRepository;
	
	@Override
	public MessageResponse save(Agence agence) {
		Agence existe = agenceRepository.findByCode(agence.getCode());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cette agence existe déja !");
        }
        agence.setActif(1);
        agenceRepository.save(agence);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
	}

	@Override
	public MessageResponse update(Long id, Agence agence) {
		Agence existe = agenceRepository.findById(id).orElse(null);
        if (existe==null){
            return new MessageResponse(false,"Echec !","Cette agence n'existe pas !");
        }
        agence.setActif(1);
        agence.setId(id);
        agenceRepository.save(agence);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
	}

	@Override
	public MessageResponse Bloquer(Long id) {
		Agence agence = agenceRepository.findById(id).orElse(null);
		if (agence==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		agence.setActif(0);
		agence.setId(id);
		agenceRepository.save(agence);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}

	@Override
	public MessageResponse Debloquer(Long id) {
		Agence agence = agenceRepository.findById(id).orElse(null);
		if (agence==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		agence.setActif(1);
		agence.setId(id);
		agenceRepository.save(agence);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");

	}

	@Override
	public List<Agence> findAllOpen() {
		return agenceRepository.findAllDebloquer();
	}

	@Override
	public List<Agence> findAllClosed() {
		return agenceRepository.findAllBloquer();
	}

	@Override
	public Agence findById(Long id) {
		Agence agence = agenceRepository.findById(id).orElse(null);
		return agence;
	}

}

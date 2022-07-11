package com.sb.Assurance.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.entities.Agent;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.AgenceRepository;
import com.sb.Assurance.repository.AgentRepository;

@Service
public class AgentServiceImpl implements AgentService{

	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	AgenceRepository agenceRepository;
	
	@Transactional
	@Override
	public MessageResponse save(Agent agent,Long idAgence) {
		Agent existe = agentRepository.findByCin(agent.getCin());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cet agent existe déja !");
        }
        Agence a = agenceRepository.findById(idAgence).orElse(null);
        agent.setActif(1);
        agent.setAgence(a);
        agentRepository.save(agent);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
	}

	@Transactional
	@Override
	public MessageResponse update(Long id,Long idAgence,Agent agent) {
		Agent existe = agentRepository.findById(id).orElse(null);
        if (existe==null){
        	
            return new MessageResponse(false,"Echec !","Cet agent n'existe pas !");

        }
        Agence a = agenceRepository.findById(idAgence).orElse(null);
        agent.setActif(1);
        agent.setAgence(a);
        agent.setId(id);
  
        agentRepository.save(agent);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }

	@Override
	public MessageResponse Bloquer(Long id) {
		Agent agent = agentRepository.findById(id).orElse(null);
		if (agent==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		agent.setActif(0);
		agent.setId(id);
		agentRepository.save(agent);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");
	}

	@Override

	public MessageResponse Debloquer(Long id) {
		Agent agent = agentRepository.findById(id).orElse(null);
		if (agent==null) {
			return new MessageResponse(false, "Echec !", "Opération non réalisée !");

		}
		agent.setActif(1);
		agent.setId(id);
		agentRepository.save(agent);
		return new MessageResponse(true, "Succès", "Opération réalisée avec succès.");

	}

	@Transactional
    @Override
	public List<Agent> findAllDebloquer() {
		return agentRepository.findAllDebloquer();
	}

	@Transactional
    @Override
	public List<Agent> findAllBloquer() {
		return agentRepository.findAllBloquer();
	}

	@Transactional
    @Override
	public Agent findById(Long id) {
		Agent agent = agentRepository.findById(id).orElse(null);
		return agent;
	}

}

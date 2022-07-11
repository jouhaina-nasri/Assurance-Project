package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Agent;
import com.sb.Assurance.reponses.MessageResponse;

public interface AgentService {


	public MessageResponse save(Agent assurance,Long id);
    public MessageResponse update(Long id,Long idAgence,Agent assurance);
    public MessageResponse Bloquer(Long id);
    public MessageResponse Debloquer(Long id);
    public List<Agent> findAllDebloquer();
    public List<Agent> findAllBloquer();
    public Agent findById(Long id);
}

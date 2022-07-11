package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Classe;
import com.sb.Assurance.reponses.MessageResponse;



public interface ClasseService {

	public MessageResponse save(Classe classe);
    public MessageResponse update(int id,Classe classe);
    public List<Classe> findAll();
    public Classe findByClasse(int id);
}

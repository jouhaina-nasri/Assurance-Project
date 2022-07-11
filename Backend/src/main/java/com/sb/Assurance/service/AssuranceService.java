package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Assurance;
import com.sb.Assurance.reponses.MessageResponse;


public interface AssuranceService {

	public MessageResponse save(Assurance assurance);
    public MessageResponse update(int id,Assurance assurance);
    public List<Assurance> findAll();
    public Assurance findByMF(int id);
}

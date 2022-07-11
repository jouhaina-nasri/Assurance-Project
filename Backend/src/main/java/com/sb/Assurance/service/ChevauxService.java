package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Chevaux;
import com.sb.Assurance.reponses.MessageResponse;


public interface ChevauxService {
	
	public MessageResponse save(Chevaux chevaux);
    public MessageResponse update(int id,Chevaux chevaux);
    public List<Chevaux> findAll();
    public Chevaux findByCV(int id);

}

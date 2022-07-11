package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Commerciale;
import com.sb.Assurance.reponses.MessageResponse;

public interface CommercialeService {
	
	public MessageResponse save(Commerciale commerciale);
    public MessageResponse update(int id,Commerciale commerciale);
    public List<Commerciale> findAll();
    public Commerciale findByMF(int id);

}

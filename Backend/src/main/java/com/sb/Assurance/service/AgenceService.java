package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Agence;
import com.sb.Assurance.reponses.MessageResponse;

public interface AgenceService {
	

	public MessageResponse save(Agence assurance);
    public MessageResponse update(Long id,Agence assurance);
    public MessageResponse Bloquer(Long id);
    public MessageResponse Debloquer(Long id);
    public List<Agence> findAllOpen();
    public List<Agence> findAllClosed();
    public Agence findById(Long id);

}

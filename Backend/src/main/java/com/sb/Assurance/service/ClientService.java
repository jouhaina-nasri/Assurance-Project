package com.sb.Assurance.service;

import java.util.List;
import com.sb.Assurance.entities.Client;
import com.sb.Assurance.reponses.MessageResponse;

public interface ClientService {
	
	public MessageResponse save(Client classe,Long id);
    public MessageResponse update(Long id,Long idAgence,Client classe);
    public MessageResponse Bloquer(Long id);
    public MessageResponse Debloquer(Long id);
    public List<Client> findAllDebloquer();
    public List<Client> findAllBloquer();
    public Client findById(Long id);
    public Client findByCin(Long id);

}

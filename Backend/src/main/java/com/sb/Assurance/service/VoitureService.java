package com.sb.Assurance.service;

import java.util.List;

import com.sb.Assurance.entities.Voiture;
import com.sb.Assurance.reponses.MessageResponse;

public interface VoitureService {

	public MessageResponse save(Voiture classe,Long id);
    public MessageResponse update(Voiture classe,Long idVoiture,Long idClient);
    public MessageResponse delete(Long idVoiture);
    public List<Voiture> findAll();
    public Voiture findById(Long id);
    public double calcul(String serie);
}

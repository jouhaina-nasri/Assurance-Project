package com.sb.Assurance.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.Assurance.entities.Commerciale;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.CalculComRepository;

import java.util.List;

@Service

public class CommercialeServiceImpl implements CommercialeService{

@Autowired
CalculComRepository assuranceRepository;

    @Transactional
    @Override
    public MessageResponse save(Commerciale commerciale) {
    	Commerciale existe = assuranceRepository.findByMF(commerciale.getMF());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cet assurance commerciale existe déja !");
        }
        assuranceRepository.save(commerciale);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Transactional
    @Override
    public MessageResponse update(int id,Commerciale commerciale) {
    	Commerciale existe = assuranceRepository.findByMF(id);
        if (existe==null){
        	
            return new MessageResponse(false,"Echec !","Cet assurance commerciale existe déja !");

        }
        commerciale.setMF(id);
        assuranceRepository.save(commerciale);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Override
    public List<Commerciale> findAll() {

        return assuranceRepository.findAll();
    }

    @Override
    public Commerciale findByMF(int id) {
    	Commerciale commerciale = assuranceRepository.findByMF(id);
        return commerciale;
    }
}
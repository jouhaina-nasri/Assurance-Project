package com.sb.Assurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.Assurance.entities.Chevaux;
import com.sb.Assurance.reponses.MessageResponse;

import com.sb.Assurance.repository.ChevauxRepository;

@Service
public class ChevauxServiceImpl implements ChevauxService{

@Autowired
ChevauxRepository assuranceRepository;


    @Transactional
    @Override
    public MessageResponse save(Chevaux chevaux) {
    	Chevaux existe = assuranceRepository.findByCV(chevaux.getCV());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");
        }
        assuranceRepository.save(chevaux);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Transactional
    @Override
    public MessageResponse update(int id,Chevaux chevaux) {
    	Chevaux existe = assuranceRepository.findByCV(id);
        if (existe==null){
        	
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");

        }
        existe.setRC(chevaux.getRC());
        existe.setRC1(chevaux.getRC1());
        assuranceRepository.save(existe);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Override
    public List<Chevaux> findAll() {

        return assuranceRepository.findAll();
    }

    @Override
    public Chevaux findByCV(int id) {
    	Chevaux assurance = assuranceRepository.findByCV(id);
        return assurance;
    }

}

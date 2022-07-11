package com.sb.Assurance.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.Assurance.entities.Assurance;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.CalculRepository;

import java.util.List;

@Service
public class AssuranceServiceImpl implements AssuranceService{

@Autowired
CalculRepository assuranceRepository;

    @Transactional
    @Override
    public MessageResponse save(Assurance assurance) {
    	Assurance existe = assuranceRepository.findByMF(assurance.getMF());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");
        }
        assuranceRepository.save(assurance);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Transactional
    @Override
    public MessageResponse update(int id,Assurance assurance) {
    	Assurance existe = assuranceRepository.findByMF(id);
        if (existe==null){
        	
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");

        }
        existe.setAWA(assurance.getAWA());
        existe.setBGL(assurance.getBGL());
        existe.setCP(assurance.getCP());
        existe.setFG(assurance.getFG());
        existe.setFSSR(assurance.getFSSR());
        existe.setINcen(assurance.getINcen());
        existe.setIndAcc(assurance.getIndAcc());
        existe.setPTA(assurance.getPTA());
        existe.setTaxe(assurance.getTaxe());
        existe.setTBGL(assurance.getTBGL());
        existe.setVOL(assurance.getVOL());
        
        assuranceRepository.save(existe);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Override
    public List<Assurance> findAll() {

        return assuranceRepository.findAll();
    }

    @Override
    public Assurance findByMF(int id) {
    	Assurance assurance = assuranceRepository.findByMF(id);
        return assurance;
    }
}

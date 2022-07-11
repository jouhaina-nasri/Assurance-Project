package com.sb.Assurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sb.Assurance.entities.Classe;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.ClasseRepository;

@Service
public class ClasseServiceImpl implements ClasseService{

@Autowired
ClasseRepository assuranceRepository;


    @Transactional
    @Override
    public MessageResponse save(Classe classe) {
    	 Classe existe = assuranceRepository.findByC(classe.getClasse());
        if (existe!=null){
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");
        }
        assuranceRepository.save(classe);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Transactional
    @Override
    public MessageResponse update(int id,Classe classe) {
    	Classe existe = assuranceRepository.findByC(id);
        if (existe==null){
        	
            return new MessageResponse(false,"Echec !","Cet assurance existe déja !");

        }
        
        existe.setTaux(classe.getTaux());
        assuranceRepository.save(classe);
        return new MessageResponse(true,"Succès","Opération réalisée avec succès.");
    }


    @Override
    public List<Classe> findAll() {

        return assuranceRepository.findAll();
    }

    @Override
    public Classe findByClasse(int id) {
    	Classe assurance = assuranceRepository.findByC(id);
        return assurance;
    }

}

package com.sb.Assurance.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sb.Assurance.entities.Client;
import com.sb.Assurance.entities.Typevoiture;
import com.sb.Assurance.entities.Voiture;
import com.sb.Assurance.reponses.MessageResponse;
import com.sb.Assurance.repository.ClientRepository;
import com.sb.Assurance.repository.VoitureRepository;

@Service
public class VoitureServiceImpl implements VoitureService{
	
	@Autowired
	VoitureRepository voitureRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	
	@Autowired
	CalculServiceImpl assuranceService;

	
	 @Autowired
	 CalculCommercialeServiceImpl commercialeService;
	 
	 @Autowired
	 CalculTaxiServiceImpl taxiService;
	 
	@Transactional
	@Override
	public MessageResponse save(Voiture voiture, Long id) {
		 Voiture existe = voitureRepository.findBySerie(voiture.getMatricule());
		    if (existe!=null){
		        return new MessageResponse(false,"Echec !","Cette voiture existe déja !");
		    }
		    Client client = clientRepository.findByCin(id);
		    voiture.setClient(client);
		    voitureRepository.save(voiture);
		    return new MessageResponse(true,"Succès","Voiture ajouté avec succès.");
	}

	@Transactional
	@Override
	public MessageResponse update(Voiture voiture, Long idVoiture, Long idClient) {
		boolean existe = voitureRepository.existsById(idVoiture);
	    if (!existe){
	        return new MessageResponse(false,"Echec !","Cette voiture n'existe pas !");
	    }
	    Client client = clientRepository.findByCin(idClient);
	    voiture.setClient(client);
	    voiture.setId(idVoiture);
	    voitureRepository.save(voiture);
	    return new MessageResponse(true,"Succès","Voiture ajoutée avec succès.");

	}

	@Override
	public List<Voiture> findAll() {
		return voitureRepository.findAll();
	}

	@Override
	public Voiture findById(Long id) {
		Voiture trainer = voitureRepository.findById(id).orElse(null);
		return trainer;
	}

	@Override
	public MessageResponse delete(Long idVoiture) {
		Voiture structure = findById(idVoiture);
		if (structure == null) {
			return new MessageResponse(false, "Echec", "Cet enregistrement n'existe pas !");
		}
		voitureRepository.delete(structure);
		return new MessageResponse(true, "Succès", "L'enregistrement a été supprimé avec succès.");
	}

	@Override
	public double calcul(String serie) {
		Voiture voiture = voitureRepository.findBySerie(serie);
		Client client = voiture.getClient();

		Typevoiture type=voiture.getType();
		
		double montant=0;
		if(type.name().equals("privee")) 
		{
			montant = assuranceService.CalculApayer(voiture.getCv(),voiture.getMf(),voiture.getPrix(),client.getClasse(),voiture.getNbplace(),voiture.getBgl(),voiture.getAssist());
		}
		else if(type.name().equals("commerciale"))  {
			montant =  commercialeService.CalculApayer(voiture.getCv(),voiture.getMf(),voiture.getPrix(),client.getClasse(),voiture.getNbplace(),voiture.getBgl(),voiture.getAssist());
		}
		else if(type.name().equals("taxi"))  {
			montant =  taxiService.CalculApayer(voiture.getMf(),voiture.getPrix(),client.getClasse(),voiture.getNbplace(),voiture.getBgl(),voiture.getAssist());
		}
		return montant;
	}

}

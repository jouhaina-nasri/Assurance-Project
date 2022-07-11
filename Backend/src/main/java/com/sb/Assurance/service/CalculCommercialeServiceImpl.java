package com.sb.Assurance.service;

import com.sb.Assurance.entities.Commerciale;
import com.sb.Assurance.entities.Chevaux;
import com.sb.Assurance.repository.CalculComRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculCommercialeServiceImpl implements CalculCommercialeService 
{

	@Autowired
	CalculComRepository commercialeRepository;
	@Override
	public Commerciale findByVoiture(int MF, double voiture,int nbplace,int BGL) {
		voiture =voiture*1000;
		Commerciale as = commercialeRepository.findByMF(MF);
		if(MF==6)
		{
			double vol=as.getVOL();
		    vol=vol+(voiture*2.36)/2;
			as.setVOL(vol);
			double Incen=as.getINcen();
			Incen=Incen+(voiture*3.93)/2;
			as.setINcen(Incen);
		}
		else 
			{
				double vol=as.getVOL();
			    vol=vol+voiture*2.36;
				as.setVOL(vol);
				double Incen=as.getINcen();
				Incen=Incen+voiture*3.93;
				as.setINcen(Incen);
			}
			
			int PTA =as.getPTA();
			PTA=PTA*(nbplace-1);
			as.setPTA(PTA);
			
			double BG = as.getBGL()*BGL*as.getTBGL();
			as.setBGL(BG);
	        return as;
		}
		
	@Override
	public double CalculApayer(int cv, int MF, double voiture, int classe, int nbplace, int BGL, int Assistance) {
		
		Commerciale as = findByVoiture(MF, voiture, nbplace, BGL);
		Chevaux ch = ChercherCV(cv);
		double RC1 = ch.getRC1();
		
		double taux = ChercherClasse(classe);
		
		if(as.getMF()==6) 
		{
			RC1=(RC1*taux)/2;
		}
		else {RC1=(RC1*taux);}
		
		double PNette=0;
		if(Assistance==1)
		{ PNette = RC1 +as.getCAS()+as.getVOL()+as.getINcen()+as.getBGL()+as.getAssitance()+as.getIndAcc()+as.getPTA();
		}
		else if(Assistance==0)
		{
			PNette = RC1 +as.getCAS()+as.getVOL()+as.getINcen()+as.getBGL()+as.getIndAcc()+as.getPTA();
		}
		
		double taxe = (PNette+as.getCP())*as.getTaxe();
		double FG = (RC1+as.getCP())*as.getFG();
		double PTotal= PNette+taxe+FG+as.getCP();
		double APayer= PTotal+as.getFSSR();
		APayer=Math.round(APayer);
		APayer=APayer/1000;
		return APayer;
}

	@Override
	public Chevaux ChercherCV(int cv) 
	{	Chevaux c = null;
			if(cv==2) {c = commercialeRepository.findByCV(2);}
			else if(cv==3||cv==4) {c = commercialeRepository.findByCV(3);}
			else if(cv==5||cv==6) {c = commercialeRepository.findByCV(5);}
			else if(cv==7||cv==8||cv==9||cv==10) {c = commercialeRepository.findByCV(7);}
			else if(cv==11||cv==12|cv==13||cv==14) {c = commercialeRepository.findByCV(11);}
			else if(cv>=15){c = commercialeRepository.findByCV(15);}
			else {c = null;}
			
		return c;
	}

	@Override
	public double ChercherClasse(int classe) {
		double c = commercialeRepository.findByClasse(classe);
		return c;
	}

}




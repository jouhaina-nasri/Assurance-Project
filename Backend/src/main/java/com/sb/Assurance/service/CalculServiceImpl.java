package com.sb.Assurance.service;

import com.sb.Assurance.entities.Assurance;
import com.sb.Assurance.entities.Chevaux;
import com.sb.Assurance.repository.CalculRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CalculServiceImpl implements CalculService
{

	@Autowired
	CalculRepository assuranceRepository;

  
	@Override
	public Assurance findByVoiture(int MF, double voiture,int nbplace,int BGL) {
		
		voiture=voiture*1000;
		Assurance as = assuranceRepository.findByMF(MF);
		if(MF==6)
		{
			double vol=as.getVOL();
		    vol=vol+(voiture*2.36)/2;
			as.setVOL(vol);
			double Incen=as.getINcen();
			Incen=Incen+(voiture*2.75)/2;
			as.setINcen(Incen);
		}
		else 
		{
			double vol=as.getVOL();
		    vol=vol+voiture*2.36;
			as.setVOL(vol);
			double Incen=as.getINcen();
			Incen=Incen+voiture*2.75;
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
		
		Assurance as = findByVoiture(MF, voiture, nbplace, BGL);
		Chevaux ch = ChercherCV(cv);
		double RC = ch.getRC();
		double taux = ChercherClasse(classe);
		
		if(as.getMF()==6) 
		{
			RC=(RC*taux)/2;
		}
		else {RC=(RC*taux);}
		
		double PNette=0;
		if(Assistance==1)
		{ 
			PNette = RC +as.getAWA()+as.getVOL()+as.getINcen()+as.getBGL()+as.getAssitance()+as.getIndAcc()+as.getPTA();
		}
		else if(Assistance==0)
		{
			PNette = RC +as.getAWA()+as.getVOL()+as.getINcen()+as.getBGL()+as.getIndAcc()+as.getPTA();
		}	
		double taxe = (PNette+as.getCP())*as.getTaxe();
		double FG = (RC+as.getCP())*as.getFG();
		double PTotal= PNette+taxe+FG+as.getCP();
		double APayer= PTotal+as.getFSSR();
		APayer=Math.round(APayer);
		APayer=APayer/1000;
		
		return APayer;
	}

	@Override
	public Chevaux ChercherCV(int cv) 
	{	Chevaux c = null;
			if(cv==2) {c = assuranceRepository.findByCV(2);}
			else if(cv==3||cv==4) {c = assuranceRepository.findByCV(3);}
			else if(cv==5||cv==6) {c = assuranceRepository.findByCV(5);}
			else if(cv==7||cv==8||cv==9||cv==10) {c = assuranceRepository.findByCV(7);}
			else if(cv==11||cv==12|cv==13||cv==14) {c = assuranceRepository.findByCV(11);}
			else if(cv>=15){c = assuranceRepository.findByCV(15);}
			else {c = null;}
			
		return c;
	}

	@Override
	public double ChercherClasse(int classe) {
		double c = assuranceRepository.findByClasse(classe);
		return c;
	}

}

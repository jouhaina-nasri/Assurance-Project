package com.sb.Assurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sb.Assurance.entities.Taxi;
import com.sb.Assurance.repository.TaxiRepository;

@Service
public class CalculTaxiServiceImpl  implements CalculTaxiService
{

	@Autowired
	TaxiRepository taxiRepository;
	
	@Override
	public Taxi findByVoiture(int MF, double voiture,int nbplace) {
		
		voiture=voiture*1000;
		Taxi as = taxiRepository.findByMF(MF);
		if(MF==6)
		{
			double vol=as.getVOL();
		    vol=vol+(voiture*4.78)/2;
			as.setVOL(vol);
			
			double Incen=as.getINcen();
			Incen=Incen+(voiture*4.91)/2;
			as.setINcen(Incen);
			
		}
		else 
			{
				double vol=as.getVOL();
			    vol=vol+voiture*4.78;
				as.setVOL(vol);
				double Incen=as.getINcen();
				Incen=Incen+voiture*4.91;
				as.setINcen(Incen);
			}
			
	        return as;
		}
		
	@Override
	public double CalculApayer(int MF, double voiture, int classe, int nbplace, int BGL, int Assistance) {
		
		Taxi as = findByVoiture(MF, voiture, nbplace);
		
		double taux = ChercherClasse(classe);
		double RC = as.getRc();
		
		if(as.getMF()==6) 
		{
			RC=RC*taux*0.50411;
			BGL = (BGL * as.getTBGL())/2;
		}
		else 
		{
			RC=(RC*taux);
			BGL = BGL * as.getTBGL();
		}
		
		double PNette=0;
		if(Assistance==1)
		{ 
			PNette = RC +as.getCAS()+as.getVOL()+as.getINcen()+BGL+as.getAssitance()+as.getIndAcc()+as.getPTA();
		}
		else if(Assistance==0)
		{
			PNette = RC +as.getCAS()+as.getVOL()+as.getINcen()+BGL+as.getIndAcc()+as.getPTA();
		}
		double taxe = (PNette+as.getCP())*as.getTaxe();
		
		double FG = (as.getCP()+RC)*as.getFG();
		
		double PTotal= PNette+taxe+FG+as.getCP();
		
		double APayer= PTotal+as.getFSSR();
		
		APayer=Math.round(APayer);
		APayer=APayer/1000;
		return APayer;
	}

	@Override
	public double ChercherClasse(int classe) {
		double c = taxiRepository.findByClasse(classe);
		return c;
	}

}

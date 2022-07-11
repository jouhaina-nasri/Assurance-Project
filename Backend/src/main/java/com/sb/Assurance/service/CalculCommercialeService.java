package com.sb.Assurance.service;

import com.sb.Assurance.entities.Commerciale;
import com.sb.Assurance.entities.Chevaux;

public interface CalculCommercialeService {
	
	Chevaux   ChercherCV(int cv);
	double    ChercherClasse(int classe);
    Commerciale findByVoiture(int MF, double voiture,int nbplace,int BGL);
    double CalculApayer(int cv,int MF, double voiture,int classe,int nbplace,int BGL,int Assistance);
}




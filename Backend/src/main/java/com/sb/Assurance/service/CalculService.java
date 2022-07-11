package com.sb.Assurance.service;

import com.sb.Assurance.entities.Assurance;
import com.sb.Assurance.entities.Chevaux;


public interface CalculService {
	Chevaux   ChercherCV(int cv);
	double    ChercherClasse(int classe);
    Assurance findByVoiture(int MF, double voiture,int nbplace,int BGL);
    double CalculApayer(int cv,int MF, double voiture,int classe,int nbplace,int BGL,int Assistance);
}

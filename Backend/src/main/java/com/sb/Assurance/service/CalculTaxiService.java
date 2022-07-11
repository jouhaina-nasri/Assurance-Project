package com.sb.Assurance.service;

import com.sb.Assurance.entities.Taxi;

public interface CalculTaxiService {

	double    ChercherClasse(int classe);
    Taxi findByVoiture(int MF, double voiture,int nbplace);
    double CalculApayer(int MF, double voiture,int classe,int nbplace,int BGL,int Assistance);

}

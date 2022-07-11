package com.sb.Assurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sb.Assurance.service.CalculTaxiServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/calculTaxi")
public class CalculTaxiController {

    @Autowired
    private CalculTaxiServiceImpl taxiService;

   
    @PreAuthorize("hasAuthority('User')")
   @GetMapping("/calculPayer/{MF}/{voiture}/{classe}/{nbplace}/{BGL}/{Assis}")
   public double CalculApayer(@PathVariable("MF") int MF,@PathVariable("voiture") int vot,@PathVariable("classe") int classe,@PathVariable("nbplace") int nbplace,@PathVariable("BGL") int bgl,@PathVariable("Assis") int Assis) {
	   double a = taxiService.CalculApayer(MF,vot,classe,nbplace,bgl,Assis);
	   return  a;
   }
   

}

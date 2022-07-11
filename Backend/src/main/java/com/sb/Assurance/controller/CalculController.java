package com.sb.Assurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sb.Assurance.service.CalculServiceImpl;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/calcul")
public class CalculController {

    @Autowired
    private CalculServiceImpl assuranceService;

    @PreAuthorize("hasAuthority('User')")
   @GetMapping("/calculPayer/{code}/{MF}/{voiture}/{classe}/{nbplace}/{BGL}/{Assis}")
   public double CalculApayer(@PathVariable("code") int cv,@PathVariable("MF") int MF,@PathVariable("voiture") int vot,@PathVariable("classe") int classe,@PathVariable("nbplace") int nbplace,@PathVariable("BGL") int bgl,@PathVariable("Assis") int Assis) {
	   double a = assuranceService.CalculApayer(cv,MF,vot,classe,nbplace,bgl,Assis);
	   return  a;
   }
   

}



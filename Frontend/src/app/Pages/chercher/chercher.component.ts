import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ChercherService } from 'src/app/Services/Chercher/chercher.service';

@Component({
  selector: 'app-chercher',
  templateUrl: './chercher.component.html',
  styleUrls: ['./chercher.component.scss']
})
export class ChercherComponent implements OnInit {
  matricule:string;
  Formulaire:FormGroup;
  resultat:number;
  onSubmit(){
    this.matricule  = this.Formulaire.value.matricule;
    this.calculer();
  }
  
  calculer(){
    this.chercherservice.get(this.matricule).subscribe( data =>{
      console.log(data);
      this.resultat=data;
    },
    error => console.log(error));
  }

  constructor(private chercherservice:ChercherService) { }

  ngOnInit(): void {
    this.Formulaire = new FormGroup({
      matricule: new FormControl(),
    });
  }

}

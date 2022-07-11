import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { Router } from '@angular/router';
import { Voiture } from 'src/app/Models/Voiture/voiture';
import { VoitureService } from 'src/app/Services/Voiture/voiture.service';

interface Type {
  value: string;
  viewValue: string;
}
interface BGL {
  value: number;
  viewValue: number;
}

interface Chevaux {
  value: number;
  viewValue: number;
}

interface MF {
  value: number;
  viewValue: number;
}

@Component({
  selector: 'app-addvoiture',
  templateUrl: './addvoiture.component.html',
  styleUrls: ['./addvoiture.component.scss']
})
export class AddvoitureComponent implements OnInit {
  selectedValue: string;
  selectedValue1: string;
  selectedValue2: string;
  
  chevaux: Chevaux[] = [
    {value: 2, viewValue: 2},
    {value: 3, viewValue: 3},
    {value: 4, viewValue: 4},
    {value: 5, viewValue: 5},
    {value: 6, viewValue: 6},
    {value: 7, viewValue: 7},
    {value: 8, viewValue: 8},
    {value: 9, viewValue: 9},
    {value: 10, viewValue: 10},
    {value: 11, viewValue: 11},
    {value: 12, viewValue: 12},
    {value: 13, viewValue: 13},
    {value: 14, viewValue: 14},
    {value: 15, viewValue: 15}
  ];

  mf: MF[] = [
    {value: 12, viewValue: 12},
    {value: 6, viewValue: 6},
  ];

  types: Type[] = [
    {value: 'privee', viewValue: 'privee'},
    {value: 'commerciale', viewValue: 'commerciale'},
    {value: 'taxi', viewValue: 'taxi'},
  ];

  voiture= new Voiture();
  Formulaire:FormGroup;

  onSubmit(){
  
    this.voiture.matricule  = this.Formulaire.value.matricule;
    this.voiture.prix = this.Formulaire.value.prix;
    this.voiture.type  = this.Formulaire.value.type;
    this.voiture.nbplace = this.Formulaire.value.nbplace;
    this.voiture.cv  = this.Formulaire.value.cv;
    this.voiture.mf = this.Formulaire.value.mf;
    this.voiture.cin  = this.Formulaire.value.cin;
    console.log(this.voiture);
    this.saveClient();
  }
  hideRequiredControl = new FormControl(false);
  hideRequiredControl1 = new FormControl(false);
  bg: BGL[] = [
    {value: 500, viewValue: 500},
    {value: 700, viewValue: 700},
    {value: 1000, viewValue: 1000},
  ];

  radioChange($event: MatRadioChange) {
    console.log($event.value.value);
    this.voiture.bgl=$event.value.value;
    if ($event.source.name === 'radioOpt1') {
  
    }
}

  saveClient(){
    if(this.hideRequiredControl1.value==true) 
    {
      this.voiture.assist=1;
    }
    else if (this.hideRequiredControl1.value==false) 
    {
      this.voiture.assist=0;
    }
    if(this.hideRequiredControl.value==false) 
    {
      this.voiture.bgl=0;
    }
    this.voitureService.createVoiture(this.voiture.cin,this.voiture).subscribe( data =>{
      console.log(data);
      this.goToList();
    },
    error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/voiture']);
  }

  constructor(private voitureService : VoitureService ,private router: Router) { }

  ngOnInit(): void {

    this.Formulaire = new FormGroup({
      matricule: new FormControl(),
      prix: new FormControl(),
      prenom: new FormControl(),
      type: new FormControl(),
      nbplace: new FormControl(),
      bgl: new FormControl(),
      assist: new FormControl(),
      cv: new FormControl(),
      mf: new FormControl(),
      cin: new FormControl(),
    })
  }

}

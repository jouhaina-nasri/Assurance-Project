import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { CalculService } from 'src/app/Services/Calcul/calcul.service';


interface Chevaux {
  value: number;
  viewValue: number;
}

interface MF {
  value: number;
  viewValue: number;
}

interface Classe {
  value: number;
  viewValue: number;
}

interface BGL {
  value: number;
  viewValue: number;
}

@Component({
  selector: 'app-calcul',
  templateUrl: './calcul.component.html',
  styleUrls: ['./calcul.component.scss']
})
export class CalculComponent implements OnInit {
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

  classe: Classe[] = [
    {value: 1, viewValue: 1},
    {value: 2, viewValue: 2},
    {value: 3, viewValue: 3},
    {value: 4, viewValue: 4},
    {value: 5, viewValue: 5},
    {value: 6, viewValue: 6},
    {value: 7, viewValue: 7},
    {value: 8, viewValue: 8},
  ];

  Formulaire:FormGroup;
  
  cv : number;
  mois:number;
  c : number;
  prix:number;
  bgl:number;
  nbplace:number;
  assist:number;
  resultat:number;
 
  onSubmit(){
    this.cv  = this.Formulaire.value.cv;
    this.mois = this.Formulaire.value.mf;
    this.c  = this.Formulaire.value.prix;
    this.prix = this.Formulaire.value.classe;
    this.nbplace  = this.Formulaire.value.nbplace;
    this.assist = this.Formulaire.value.assist;
   
    this.calculer();
  }
  
  bg: BGL[] = [
    {value: 500, viewValue: 500},
    {value: 700, viewValue: 700},
    {value: 1000, viewValue: 1000},
  ];

  radioChange($event: MatRadioChange) {
    console.log($event.value.value);
    this.bgl=$event.value.value;
    if ($event.source.name === 'radioOpt1') {
  
    }
}
hideRequiredControl = new FormControl(false);
hideRequiredControl1 = new FormControl(false);
  calculer(){
  if(this.hideRequiredControl1.value==true) 
  {
    this.assist=1;
  }
  else if (this.hideRequiredControl1.value==false) 
  {
    this.assist=0;
  }
  if(this.hideRequiredControl.value==false) 
  {
    this.bgl=0;
  }
  console.log(this.cv,this.mois,this.c,this.prix,this.nbplace,this.bgl,this.assist);
    this.calculservice.get(this.cv,this.mois,this.c,this.prix,this.nbplace,this.bgl,this.assist).subscribe( data =>{
      console.log(data);
      this.resultat=data;
    },
    error => console.log(error));
  }

  constructor(private _formBuilder: FormBuilder,private calculservice:CalculService) { }

  ngOnInit(): void {

    this.Formulaire = new FormGroup({
      assist: new FormControl(),
      nbplace: new FormControl(),
      bgl: new FormControl(),
      prix: new FormControl(),
      classe: new FormControl(),
      cv: new FormControl(),
      mf: new FormControl(),
    })
  }

}

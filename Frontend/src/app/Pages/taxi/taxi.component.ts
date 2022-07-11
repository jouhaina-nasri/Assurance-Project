import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { CalcultaxiService } from 'src/app/Services/CalculTaxi/calcultaxi.service';
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
  selector: 'app-taxi',
  templateUrl: './taxi.component.html',
  styleUrls: ['./taxi.component.scss']
})
export class TaxiComponent implements OnInit {
  selectedValue1: string;
  selectedValue2: string;

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
  
  mois:number;
  c : number;
  prix:number;
  bgl:number;
  nbplace:number;
  assist:number;
  resultat:number;
  onSubmit(){
    this.mois = this.Formulaire.value.mf;
    this.c  = this.Formulaire.value.prix;
    this.prix = this.Formulaire.value.classe;
    this.nbplace  = this.Formulaire.value.nbplace;
    this.assist = this.Formulaire.value.assist;
    this.calculer();
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
    this.bgl=$event.value.value;
    if ($event.source.name === 'radioOpt1') {
  
    }
}

  calculer(){
    if(this.hideRequiredControl1.value==true) 
  {
    this.assist=1;
  }
  else 
  {
    this.assist=0;
  }
  if(this.hideRequiredControl.value==false) 
  {
    this.bgl=0;
  }
    console.log(this.mois,this.c,this.prix,this.nbplace,this.bgl,this.assist);
    this.calculservice.get(this.mois,this.c,this.prix,this.nbplace,this.bgl,this.assist).subscribe( data =>{
      console.log(data);
      this.resultat=data;
    },
    error => console.log(error));
  }

  constructor(private calculservice:CalcultaxiService) { }

  ngOnInit(): void {
    this.Formulaire = new FormGroup({
      assist: new FormControl(),
      nbplace: new FormControl(),
      bgl: new FormControl(),
      prix: new FormControl(),
      classe: new FormControl(),
      mf: new FormControl(),
    })
  }

}

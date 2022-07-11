import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { ActivatedRoute, Router } from '@angular/router';
import { Voiture } from 'src/app/Models/Voiture/voiture';
import { ClientService } from 'src/app/Services/Client/client.service';
import { VoitureService } from 'src/app/Services/Voiture/voiture.service';

interface Type {
  value: string;
  viewValue: string;
}

interface Chevaux {
  value: number;
  viewValue: number;
}

interface MF {
  value: number;
  viewValue: number;
}
interface BGL {
  value: number;
  viewValue: number;
}

@Component({
  selector: 'app-modifiervoiture',
  templateUrl: './modifiervoiture.component.html',
  styleUrls: ['./modifiervoiture.component.scss']
})
export class ModifiervoitureComponent implements OnInit {

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

  bg: any = [
    {value: 500, viewValue: 500,checked: true},
    {value: 700, viewValue: 700,checked: false},
    {value: 1000, viewValue: 1000,checked: false},
  ];
  id: number;
  @Input() voiture:Voiture=new Voiture();

  update(id: number){
    this.id = this.route.snapshot.params['id'];

    this.voitureService.getById(this.id).subscribe(data => {
      this.voiture = data;
      console.log(data);
      if(data.assist==1){
        this.hideRequiredControl1.setValue(true);
      }
      else
      this.hideRequiredControl1.setValue(false);
      if(data.bgl==0){
        this.hideRequiredControl.setValue(false);
      }
      else
      this.hideRequiredControl.setValue(true);
      if (data.bgl === 500) {
        this.selectedRadio=this.bg[0];
      }
      else if (data.bgl === 700) {
        this.selectedRadio=this.bg[1];
      }
      else if (data.bgl === 1000) {
        this.selectedRadio=this.bg[2];
      }

    }, error => console.log(error));
 
   
  }

  onSubmit(){
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
    this.voitureService.updateVoiture(this.id,this.voiture.client.cin,this.voiture).subscribe( data  =>{
      this.voiture=data;

      this.goToList();
    }
    , error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/voiture']);
  }
 
  radioChange($event: MatRadioChange) {
    console.log($event.value.value);
    this.voiture.bgl=$event.value.value;
    if ($event.source.name === 'radioOpt1') {

    }
}
selectedRadio=this.bg[0];
  constructor(private clientService:ClientService,private voitureService : VoitureService ,private router: Router,private route: ActivatedRoute) { }
  hideRequiredControl1 = new FormControl(true);
  hideRequiredControl = new FormControl(false);
  ngOnInit(): void {
    this.update(this.id);
}

}
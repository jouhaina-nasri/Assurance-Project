import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Agence } from 'src/app/Models/Agence/agence';
import { Client } from 'src/app/Models/Client/client';
import { AgenceService } from 'src/app/Services/Agence/agence.service';
import { ClientService } from 'src/app/Services/Client/client.service';

interface Classe {
  value: number;
  viewValue: number;
}

@Component({
  selector: 'app-addclient',
  templateUrl: './addclient.component.html',
  styleUrls: ['./addclient.component.scss']
})
export class AddclientComponent implements OnInit {

  hide = true;
  selectedValue: string;
  selectedValue2: string;
  @Input() agences:Agence=new Agence();


  private getAgences(){
    this.agenceService.get().subscribe(data => {
      console.log(data);
      this.agences = data;
    });
  }
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

  client= new Client();
  Formulaire:FormGroup;

  onSubmit(){
    console.log(this.client);
    this.client.cin  = this.Formulaire.value.cin;
    this.client.nom = this.Formulaire.value.nom;
    this.client.prenom  = this.Formulaire.value.prenom;
    this.client.classe = this.Formulaire.value.classe;
    this.client.agence  = this.Formulaire.value.agence;
    this.saveClient();
  }
  
  saveClient(){
    this.clientService.createClient(this.client,this.client.agence.id).subscribe( data =>{
      console.log(data);
      this.goToList();
    },
    error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/client']);
  }

  constructor(private agenceService : AgenceService,private clientService : ClientService ,private router: Router) { }

  ngOnInit(): void {
    this.getAgences();

    this.Formulaire = new FormGroup({
      cin: new FormControl(),
      nom: new FormControl(),
      prenom: new FormControl(),
      classe: new FormControl(),
      agence: new FormControl(),
    })
  }

}

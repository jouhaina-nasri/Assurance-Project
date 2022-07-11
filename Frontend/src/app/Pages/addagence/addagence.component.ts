import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Agence } from 'src/app/Models/Agence/agence';
import { AgenceService } from 'src/app/Services/Agence/agence.service';

@Component({
  selector: 'app-addagence',
  templateUrl: './addagence.component.html',
  styleUrls: ['./addagence.component.scss']
})
export class AddagenceComponent implements OnInit {

  agence= new Agence();
  Formulaire:FormGroup;

  onSubmit(){
    console.log(this.agence);
    this.agence.code  = this.Formulaire.value.code;
    this.agence.local = this.Formulaire.value.local;
    this.agence.nom = this.Formulaire.value.nom;
    this.agence.prenom = this.Formulaire.value.prenom;
    this.agence.tel = this.Formulaire.value.tel;
    this.agence.email = this.Formulaire.value.email;
    this.saveAgence();
  }
  
  saveAgence(){
    this.agenceService.createAgence(this.agence).subscribe( data =>{
      console.log(data);
      this.goToList();
    },
    error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/agence']);
  }

  constructor(private agenceService:AgenceService,private router: Router) { }

  ngOnInit(): void {
    this.Formulaire = new FormGroup({
      code: new FormControl(),
      local: new FormControl(),
      nom: new FormControl(),
      prenom: new FormControl(),
      tel: new FormControl(),
      email: new FormControl()
    })
  }

}

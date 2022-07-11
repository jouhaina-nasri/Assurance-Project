import { Component, Input, OnInit } from '@angular/core';
import { Agent } from 'src/app/Models/Agent/agent';
import { Agence } from 'src/app/Models/Agence/agence';
import { AgenceService } from 'src/app/Services/Agence/agence.service';
import { FormControl, FormGroup } from '@angular/forms';
import { AgentService } from 'src/app/Services/Agent/agent.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-addagent',
  templateUrl: './addagent.component.html',
  styleUrls: ['./addagent.component.scss']
})
export class AddagentComponent implements OnInit {
  hide = true;
  selectedValue: string;
  @Input() agences:Agence=new Agence();

  private getAgences(){
    this.agenceService.get().subscribe(data => {
      console.log(data);
      this.agences = data;
    });
  }

  agent= new Agent();
  Formulaire:FormGroup;

  onSubmit(){
    console.log(this.agent);
    this.agent.cin  = this.Formulaire.value.cin;
    this.agent.nom = this.Formulaire.value.nom;
    this.agent.prenom  = this.Formulaire.value.prenom;
    this.agent.username = this.Formulaire.value.username;
    this.agent.password = this.Formulaire.value.password;
    this.agent.agence  = this.Formulaire.value.agence;
    this.saveAgent();
  }

  saveAgent(){
    this.agentService.createAgent(this.agent.agence.id,this.agent).subscribe( data =>{
      console.log(data);
      this.goToList();
    },
    error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/agent']);
  }

  constructor(private agenceService : AgenceService,private agentService : AgentService ,private router: Router) { }

  ngOnInit(): void {
    this.getAgences();
    this.Formulaire = new FormGroup({
      cin: new FormControl(),
      nom: new FormControl(),
      prenom: new FormControl(),
      username: new FormControl(),
      password: new FormControl(),
      agence: new FormControl(),
    })
  }

}


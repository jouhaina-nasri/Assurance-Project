import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Agence } from 'src/app/Models/Agence/agence';
import { Agent } from 'src/app/Models/Agent/agent';
import { AgenceService } from 'src/app/Services/Agence/agence.service';
import { AgentService } from 'src/app/Services/Agent/agent.service';

@Component({
  selector: 'app-modifieragent',
  templateUrl: './modifieragent.component.html',
  styleUrls: ['./modifieragent.component.scss']
})
export class ModifieragentComponent implements OnInit {

  hide = true;
  selectedValue: string;

  @Input() agences:Agence=new Agence();
  id: number;
  @Input() agents:Agent=new Agent();

  private getAgences(){
    this.agenceService.get().subscribe(data => {
      console.log(data);
      this.agences = data;
    });
  }
  update(id: number){
    this.id = this.route.snapshot.params['id'];

    this.agentService.getById(this.id).subscribe(data => {
      this.agents = data;
      console.log(data);
    }, error => console.log(error));
  }

  onSubmit(){
    this.agentService.updateAgent(this.id,this.agents.agence.id,this.agents).subscribe( data  =>{
      this.agents=data;
      this.goToList();
    }
    , error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/agent']);
  }

  constructor(private agenceService : AgenceService,private agentService : AgentService ,private router: Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAgences();
    this.update(this.id);
  }

}

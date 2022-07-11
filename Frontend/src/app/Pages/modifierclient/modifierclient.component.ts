import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Agence } from 'src/app/Models/Agence/agence';
import { Client } from 'src/app/Models/Client/client';
import { AgenceService } from 'src/app/Services/Agence/agence.service';
import { ClientService } from 'src/app/Services/Client/client.service';

interface Classe {
  value: number;
  viewValue: number;
}
@Component({
  selector: 'app-modifierclient',
  templateUrl: './modifierclient.component.html',
  styleUrls: ['./modifierclient.component.scss']
})
export class ModifierclientComponent implements OnInit {

  
  hide = true;
  selectedValue: string;
  selectedValue2: string;

  @Input() agences:Agence=new Agence();
  id: number;
  @Input() clients:Client=new Client();

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

  
  update(id: number){
    this.id = this.route.snapshot.params['id'];

    this.clientService.getById(this.id).subscribe(data => {
      this.clients = data;
      console.log(data);
    }, error => console.log(error));
  }

  onSubmit(){
    this.clientService.updateClient(this.id,this.clients.agence.id,this.clients).subscribe( data  =>{
      this.clients=data;
      this.goToList();
    }
    , error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/client']);
  }

  constructor(private agenceService : AgenceService,private clientService : ClientService ,private router: Router,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAgences();
    this.update(this.id);
  }

}

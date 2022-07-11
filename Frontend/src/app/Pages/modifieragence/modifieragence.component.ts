import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Agence } from 'src/app/Models/Agence/agence';
import { AgenceService } from 'src/app/Services/Agence/agence.service';

@Component({
  selector: 'app-modifieragence',
  templateUrl: './modifieragence.component.html',
  styleUrls: ['./modifieragence.component.scss']
})
export class ModifieragenceComponent implements OnInit {

  id: number;
  @Input() agence:Agence=new Agence();
  constructor(private agenceService: AgenceService, private route: ActivatedRoute,private router: Router) { }

  update(id: number){
    this.id = this.route.snapshot.params['id'];

    this.agenceService.getById(this.id).subscribe(data => {
      this.agence = data;
      console.log(data);
    }, error => console.log(error));
  }

  onSubmit(){
    this.agenceService.updateAgence(this.id,this.agence).subscribe( data  =>{
      this.agence=data;
      this.goToList();
    }
    , error => console.log(error));
  }
  
  goToList(){
    this.router.navigate(['/menu/agence']);
  }

  ngOnInit(): void {
    this.update(this.id);
  }

}

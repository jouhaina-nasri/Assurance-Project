import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/Services/Token/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  user: any;
  role : any;
  constructor(private http: HttpClient, private router: Router,private token: TokenStorageService) {}
  ngOnInit(): void {

    this.user= this.token.getUser();
    this.role=this.user.roles;
    console.log(this.role[0]);
  }

  logout() {
    this.token.signOut();
    this.router.navigateByUrl('');
  }

}

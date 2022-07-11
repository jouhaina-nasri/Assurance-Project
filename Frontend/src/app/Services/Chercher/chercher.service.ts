import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class ChercherService {

  private ROOT_URL = 'http://localhost:8090/voiture/calcul';

  constructor(private http:HttpClient,private token:TokenStorageService) { }
  geettooken()
  {
    let  t =this.token.getToken();
    console.log(t);
    let httpOptions = {
      headers: new HttpHeaders( {'Authorization': "Bearer "+ t})
      };
      return httpOptions;
  }


  get(id:string) :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<any>(`${this.ROOT_URL}/${id}`,httpOptions );
  }
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class CalcultaxiService 
{
  private ROOT_URL = 'http://localhost:8090/calculTaxi/calculPayer';

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


  get(MF:number,voiture:number,classe:number,nbplace:number,bgl:number,Assis:number) :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<any>(`${this.ROOT_URL}/${MF}/${voiture}/${classe}/${nbplace}/${bgl}/${Assis}`,httpOptions );
  }
}

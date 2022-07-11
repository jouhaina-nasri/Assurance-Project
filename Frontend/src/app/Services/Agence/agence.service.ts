import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Agence } from 'src/app/Models/Agence/agence';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class AgenceService {

  private ROOT_URL = 'http://localhost:8090/agence';

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


  get() :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Agence[]>(`${this.ROOT_URL}/`,httpOptions );
  }

  getFermer() :Observable<Agence[]>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Agence[]>(`${this.ROOT_URL}/bloquer` ,httpOptions);
  }

  getById(id:number) :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Agence[]>(`${this.ROOT_URL}/${id}`,httpOptions );
  }

  updateAgence(id: number, agence:any): Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/${id}`, agence,httpOptions);
  }

  fermerAgence(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/fermer/${id}`, null,httpOptions);
  }

  ouvrirAgence(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/ouvrir/${id}`, null,httpOptions);
  }

  createAgence(agence: Agence): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.post(`${this.ROOT_URL}`, agence,httpOptions);
  }
}

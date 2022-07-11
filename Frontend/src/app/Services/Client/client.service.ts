import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from 'src/app/Models/Client/client';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private ROOT_URL = 'http://localhost:8090/client';

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


  get() :Observable<Client[]>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Client[]>(`${this.ROOT_URL}/`,httpOptions );
  }

  getBloquer() :Observable<Client[]>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Client[]>(`${this.ROOT_URL}/bloquer`,httpOptions );
  }

  getById(id:number) :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<Client[]>(`${this.ROOT_URL}/${id}`,httpOptions );
  }

  updateClient(id: number,idagence: number, client:any): Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/${id}/${idagence}`, client,httpOptions);
  }

  BloquerClient(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/bloquer/${id}`, null,httpOptions);
  }

  DebloquerClient(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/debloquer/${id}`, null,httpOptions);
  }

  createClient(client: Client,id:number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.post(`${this.ROOT_URL}/${id}`, client,httpOptions);
  }
}
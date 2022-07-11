import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private ROOT_URL = 'http://localhost:8090/agent';

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
    return this.http.get<any>(`${this.ROOT_URL}/`,httpOptions );
  }

  getBloquer() :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<any>(`${this.ROOT_URL}/bloquer` ,httpOptions);
  }

  getById(id:number) :Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.get<any>(`${this.ROOT_URL}/${id}`,httpOptions );
  }

  updateAgent(id: number,idagence:number, agent:any): Observable<any>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/${id}/${idagence}`, agent,httpOptions);
  }

  bloquerAgent(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/bloquer/${id}`, null,httpOptions);
  }

  debloquerAgent(id: number): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.put(`${this.ROOT_URL}/debloquer/${id}`, null,httpOptions);
  }

  createAgent(id:number,agent: any): Observable<Object>
  {
    let httpOptions = this.geettooken();
    return this.http.post(`${this.ROOT_URL}/${id}`, agent,httpOptions);
  }
}

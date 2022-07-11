import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class VoitureService 
{
   private ROOT_URL = 'http://localhost:8090/voiture';

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


getById(id:number) :Observable<any>
{
  let httpOptions = this.geettooken();
  return this.http.get<any>(`${this.ROOT_URL}/${id}`,httpOptions );
}

updateVoiture(id: number,idclient:number, voiture:any): Observable<any>
{
  let httpOptions = this.geettooken();
  return this.http.put(`${this.ROOT_URL}/${id}/${idclient}`, voiture,httpOptions);
}

createVoiture(id:number,voiture: any): Observable<Object>
{
  let httpOptions = this.geettooken();
  return this.http.post(`${this.ROOT_URL}/${id}`, voiture,httpOptions);
}

deleteVoiture(id:any):Observable<any>
{
  let httpOptions = this.geettooken();
  return this.http.delete<any>(`${this.ROOT_URL}/${id}`,httpOptions)
}
}

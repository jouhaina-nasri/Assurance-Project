import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Decision, DeclarationSinistre, Sinistre, Tache } from 'src/app/Models/Sinistre/sinistre';
import { TokenStorageService } from '../Token/token.service';

@Injectable({
  providedIn: 'root'
})
export class SinistreService {

  private ROOT_URL = 'http://localhost:8090/sinistre';

  constructor(private http: HttpClient, private token: TokenStorageService) { }

  private options() {
    return { headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.token.getToken() }) };
  }

  lister(): Observable<Sinistre[]> {
    return this.http.get<Sinistre[]>(this.ROOT_URL, this.options());
  }

  trouver(id: number): Observable<Sinistre> {
    return this.http.get<Sinistre>(`${this.ROOT_URL}/${id}`, this.options());
  }

  declarer(declaration: DeclarationSinistre): Observable<Sinistre> {
    return this.http.post<Sinistre>(this.ROOT_URL, declaration, this.options());
  }

  taches(): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.ROOT_URL}/taches`, this.options());
  }

  decider(taskId: string, decision: Decision): Observable<Sinistre> {
    return this.http.post<Sinistre>(`${this.ROOT_URL}/taches/${taskId}/decision`, decision, this.options());
  }
}

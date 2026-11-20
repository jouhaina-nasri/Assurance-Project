import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LIBELLES_TYPE, Tache } from 'src/app/Models/Sinistre/sinistre';
import { SinistreService } from 'src/app/Services/Sinistre/sinistre.service';

/** Tâches Camunda « Valider le sinistre » (rôle User) et « Expertise du sinistre » (rôle Admin). */
@Component({
  selector: 'app-taches',
  templateUrl: './taches.component.html',
  styleUrls: ['../sinistre/sinistre.component.scss']
})
export class TachesComponent implements OnInit {

  taches: Tache[] = [];
  libellesType = LIBELLES_TYPE;
  montants: { [taskId: string]: number } = {};
  commentaires: { [taskId: string]: string } = {};
  erreurs: { [taskId: string]: string } = {};
  chargement = false;

  constructor(private sinistreService: SinistreService) { }

  ngOnInit(): void {
    this.charger();
  }

  charger() {
    this.chargement = true;
    this.sinistreService.taches().subscribe(data => {
      this.taches = data;
      data.forEach(t => this.montants[t.id] = t.sinistre.montantDeclare);
      this.chargement = false;
    }, () => this.chargement = false);
  }

  decider(tache: Tache, approuve: boolean) {
    this.erreurs[tache.id] = null;
    this.sinistreService.decider(tache.id, {
      approuve,
      montantIndemnise: approuve ? this.montants[tache.id] : null,
      commentaire: this.commentaires[tache.id]
    }).subscribe(
      () => this.charger(),
      (e: HttpErrorResponse) => this.erreurs[tache.id] = (e.error && e.error.message) || 'La décision a échoué.');
  }
}

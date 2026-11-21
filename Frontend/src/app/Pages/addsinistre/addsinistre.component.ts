import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LIBELLES_ROUTAGE, LIBELLES_STATUT, LIBELLES_TYPE, Sinistre } from 'src/app/Models/Sinistre/sinistre';
import { Voiture } from 'src/app/Models/Voiture/voiture';
import { SinistreService } from 'src/app/Services/Sinistre/sinistre.service';
import { VoitureService } from 'src/app/Services/Voiture/voiture.service';

@Component({
  selector: 'app-addsinistre',
  templateUrl: './addsinistre.component.html',
  styleUrls: ['../sinistre/sinistre.component.scss']
})
export class AddsinistreComponent implements OnInit {

  types = Object.keys(LIBELLES_TYPE).map(k => ({ value: k, viewValue: LIBELLES_TYPE[k] }));
  libellesStatut = LIBELLES_STATUT;
  libellesRoutage = LIBELLES_ROUTAGE;
  voitures: Voiture[] = [];
  aujourdhui = new Date().toISOString().substring(0, 10);

  Formulaire: FormGroup;
  resultat: Sinistre = null;
  erreur: string = null;
  envoi = false;

  constructor(private sinistreService: SinistreService, private voitureService: VoitureService) { }

  ngOnInit(): void {
    this.Formulaire = new FormGroup({
      matricule: new FormControl(null, Validators.required),
      dateSinistre: new FormControl(this.aujourdhui, Validators.required),
      typeSinistre: new FormControl('collision', Validators.required),
      montantDeclare: new FormControl(null, [Validators.required, Validators.min(1)]),
      description: new FormControl(''),
      accidentNuit: new FormControl(false),
      presenceTemoins: new FormControl(false),
      constatAmiable: new FormControl(false)
    });
    this.voitureService.get().subscribe(data => this.voitures = data);
  }

  onSubmit() {
    if (this.Formulaire.invalid) {
      this.Formulaire.markAllAsTouched();
      return;
    }
    this.envoi = true;
    this.erreur = null;
    this.resultat = null;
    this.sinistreService.declarer(this.Formulaire.value).subscribe(
      s => {
        this.resultat = s;
        this.envoi = false;
      },
      (e: HttpErrorResponse) => {
        this.erreur = (e.error && e.error.message) || 'La déclaration a échoué.';
        this.envoi = false;
      });
  }

  nouvelleDeclaration() {
    this.resultat = null;
    this.Formulaire.reset({
      dateSinistre: this.aujourdhui, typeSinistre: 'collision', description: '',
      accidentNuit: false, presenceTemoins: false, constatAmiable: false
    });
  }
}

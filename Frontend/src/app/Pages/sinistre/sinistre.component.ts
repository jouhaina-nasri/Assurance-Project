import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { LIBELLES_ROUTAGE, LIBELLES_STATUT, LIBELLES_TYPE, Sinistre } from 'src/app/Models/Sinistre/sinistre';
import { SinistreService } from 'src/app/Services/Sinistre/sinistre.service';

@Component({
  selector: 'app-sinistre',
  templateUrl: './sinistre.component.html',
  styleUrls: ['./sinistre.component.scss']
})
export class SinistreComponent implements OnInit, AfterViewInit {

  displayedColumns = ['reference', 'matricule', 'typeSinistre', 'dateSinistre', 'montantDeclare',
    'scoreRisque', 'routage', 'statut', 'montantIndemnise'];
  dataSource = new MatTableDataSource<Sinistre>([]);
  libellesType = LIBELLES_TYPE;
  libellesStatut = LIBELLES_STATUT;
  libellesRoutage = LIBELLES_ROUTAGE;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private sinistreService: SinistreService) { }

  ngOnInit(): void {
    this.dataSource.filterPredicate = (s: Sinistre, filtre: string) =>
      [s.reference, s.voiture && s.voiture.matricule, s.typeSinistre, s.statut, s.niveauRisque]
        .join(' ').toLowerCase().includes(filtre);
    this.dataSource.sortingDataAccessor = (s: Sinistre, col: string) =>
      col === 'matricule' ? s.voiture.matricule : (s as any)[col];
    this.sinistreService.lister().subscribe(data => this.dataSource.data = data);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}

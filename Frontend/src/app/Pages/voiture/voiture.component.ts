import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Voiture } from 'src/app/Models/Voiture/voiture';
import { VoitureService } from 'src/app/Services/Voiture/voiture.service';

export interface PeriodicElement {
  id: number;
  serie: string;
  prix: number;
  nbplace: number;
  type: string;
}

const ELEMENT_DATA: PeriodicElement[] = [];
@Component({
  selector: 'app-voiture',
  templateUrl: './voiture.component.html',
  styleUrls: ['./voiture.component.scss']
})
export class VoitureComponent implements OnInit {
  displayedColumns: string[] = ['matricule', 'prix','nbplace','type','Actions'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  voiture : Voiture[];

  constructor(public dialog: MatDialog,private voitureservice:VoitureService) {
  }

  ngOnInit(): void {
    this.getVoiture();
   }
   
  private getVoiture(){
    this.voitureservice.get().subscribe(data => {
      console.log(data);
      this.voiture = data;
    });
  }

  private DeleteClient(id:number){
    this.voitureservice.deleteVoiture(id).subscribe( data => {
      this.getVoiture();
    })
  
}

openDialog(id:number) {
    const dialogRef = this.dialog.open(SupprimerVoiture);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      if(result){ this.DeleteClient(id);}
    });
}
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}

@Component({
  selector: 'dialog-content-example-dialog',
  templateUrl: 'supprimer.html',
})
export class SupprimerVoiture {}
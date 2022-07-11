import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Agence } from 'src/app/Models/Agence/agence';
import { AgenceService } from 'src/app/Services/Agence/agence.service';
export interface PeriodicElement {
  id: number;
  code: string;
  local: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, code: 'Hydrogen',local: 'H'},
];

@Component({
  selector: 'app-agencefermee',
  templateUrl: './agencefermee.component.html',
  styleUrls: ['./agencefermee.component.scss']
})
export class AgencefermeeComponent implements OnInit {
  agences : Agence[];

  displayedColumns: string[] = ['code', 'local','chef','contact','email','Actions'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public dialog: MatDialog, private agenceService : AgenceService) {
  }
   ngOnInit(): void {
    this.getAgences();
   }

  private getAgences(){
    this.agenceService.getFermer().subscribe(data => {
      console.log(data);
      this.agences = data;
    });
  }

  private ouvrirAgence(id:number){
    this.agenceService.ouvrirAgence(id).subscribe( data => {
      this.getAgences();
    })
  
}
  openDialog(id:number) {
    const dialogRef = this.dialog.open(OpenAgence);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      if(result){ this.ouvrirAgence(id);}
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
  templateUrl: 'open.html',
})
export class OpenAgence {}
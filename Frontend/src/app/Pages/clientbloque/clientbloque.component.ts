import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Client } from 'src/app/Models/Client/client';
import { ClientService } from 'src/app/Services/Client/client.service';

export interface PeriodicElement {
  id: number;
  nom: string;
  prenom: string;
  classe: number;
}

const ELEMENT_DATA: PeriodicElement[] = [
];

@Component({
  selector: 'app-clientbloque',
  templateUrl: './clientbloque.component.html',
  styleUrls: ['./clientbloque.component.scss']
})
export class ClientbloqueComponent implements OnInit{
  displayedColumns: string[] = ['nom', 'prenom','classe','Actions'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);

  clients : Client[];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public dialog: MatDialog,private clientservice:ClientService) {
  }

  ngOnInit(): void {
    this.getClients();
   }
   
  private getClients(){
    this.clientservice.getBloquer().subscribe(data => {
      console.log(data);
      this.clients = data;
    });
  }

  private DebloquerClient(id:number){
    this.clientservice.DebloquerClient(id).subscribe( data => {
      this.getClients();
    })
  
}

openDialog(id:number) {
    const dialogRef = this.dialog.open(DebloquerClient);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      if(result){ this.DebloquerClient(id);}
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
  templateUrl: 'debloquer.html',
})
export class DebloquerClient {}

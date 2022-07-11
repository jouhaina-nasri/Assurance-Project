import {AfterViewInit, Component, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Agent } from 'src/app/Models/Agent/agent';
import { AgentService } from 'src/app/Services/Agent/agent.service';

export interface PeriodicElement {
  id: number;
  nom: string;
  prenom: string;
  role: string;
}

const ELEMENT_DATA: PeriodicElement[] = [];
@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.scss']
})
export class AgentComponent implements AfterViewInit {
  displayedColumns: string[] = ['cin', 'nom', 'prenom','Actions'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  agents : Agent[];

  constructor(public dialog: MatDialog,private agentservice:AgentService) {
    }

    ngOnInit(): void {
      this.getAgent();
     }
     
    private getAgent(){
      this.agentservice.get().subscribe(data => {
        console.log(data);
        this.agents = data;
      });
    }
  
    private BloquerAgent(id:number){
      this.agentservice.bloquerAgent(id).subscribe( data => {
        this.getAgent();
      })
    }

  openDialog(id:number) {
      const dialogRef = this.dialog.open(SupprimerAgent);
  
      dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
        if(result){ this.BloquerAgent(id);}
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
export class SupprimerAgent {}
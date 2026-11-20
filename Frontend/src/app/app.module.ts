import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { CommonModule, DatePipe } from '@angular/common';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MenuComponent } from './Pages/menu/menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatStepperModule } from '@angular/material/stepper';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableFilterModule } from 'mat-table-filter';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { NgxMatFileInputModule } from '@angular-material-components/file-input';
import { MatTableModule } from '@angular/material/table';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSortModule } from '@angular/material/sort';
import { LoginComponent } from './login/login/login.component';
import { AccueilComponent } from './Pages/accueil/accueil.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatDividerModule} from '@angular/material/divider';
import {SidebarModule} from 'ng-sidebar';
import { MainComponent } from './Pages/main/main.component';
import { CalculComponent } from './Pages/calcul/calcul.component';
import { CommercialeComponent } from './Pages/commerciale/commerciale.component';
import { TaxiComponent } from './Pages/taxi/taxi.component';
import { AddagenceComponent } from './Pages/addagence/addagence.component';
import { AgenceComponent, SupprimerAgence } from './Pages/agence/agence.component';
import { AgentComponent, SupprimerAgent } from './Pages/agent/agent.component';
import { AddagentComponent } from './Pages/addagent/addagent.component';
import { ClientComponent, SupprimerClient } from './Pages/client/client.component';
import { AddclientComponent } from './Pages/addclient/addclient.component';
import { AddvoitureComponent } from './Pages/addvoiture/addvoiture.component';
import { SupprimerVoiture, VoitureComponent } from './Pages/voiture/voiture.component';
import { AgentbloqueComponent, DebloquerAgent } from './Pages/agentbloque/agentbloque.component';
import { ClientbloqueComponent, DebloquerClient } from './Pages/clientbloque/clientbloque.component';
import { AgencefermeeComponent, OpenAgence } from './Pages/agencefermee/agencefermee.component';
import { ChercherComponent } from './Pages/chercher/chercher.component';
import { ModifieragenceComponent } from './Pages/modifieragence/modifieragence.component';
import { ModifieragentComponent } from './Pages/modifieragent/modifieragent.component';
import { ModifierclientComponent } from './Pages/modifierclient/modifierclient.component';
import { ModifiervoitureComponent } from './Pages/modifiervoiture/modifiervoiture.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio'
import { AddsinistreComponent } from './Pages/addsinistre/addsinistre.component';
import { SinistreComponent } from './Pages/sinistre/sinistre.component';
import { TachesComponent } from './Pages/taches/taches.component';

@NgModule({
  declarations: [
    AppComponent,
    SupprimerClient,
    SupprimerVoiture,
    OpenAgence,
    SupprimerAgent,
    DebloquerAgent,
    DebloquerClient,
    MenuComponent,
    LoginComponent,
    SupprimerAgence,
    AccueilComponent,
    MainComponent,
    CalculComponent,
    CommercialeComponent,
    TaxiComponent,
    AddagenceComponent,
    AgenceComponent,
    AgentComponent,
    AddagentComponent,
    ClientComponent,
    AddclientComponent,
    AddvoitureComponent,
    VoitureComponent,
    AgentbloqueComponent,
    ClientbloqueComponent,
    AgencefermeeComponent,
    ChercherComponent,
    ModifieragenceComponent,
    ModifieragentComponent,
    ModifierclientComponent,
    ModifiervoitureComponent,
    AddsinistreComponent,
    SinistreComponent,
    TachesComponent
       ],
  imports: [  
    MatToolbarModule,
    SidebarModule.forRoot(),
    MatRadioModule,
    MatSidenavModule,
    MatDividerModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatTabsModule,  
    MatFormFieldModule,  
    BrowserAnimationsModule ,     
    AppRoutingModule,
    RouterModule,
    CommonModule,
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatStepperModule,
    MatIconModule,
    MatInputModule,
    MatTabsModule,
    MatTableFilterModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatCheckboxModule,MatCardModule,NgxMatFileInputModule,
    MatButtonModule,
    MatProgressBarModule,
    MatTableModule,
    FormsModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    MatSortModule
  ],
  providers: [MatDatepickerModule, DatePipe, { provide: MatPaginatorIntl}],

  bootstrap: [AppComponent]
})
export class AppModule { }

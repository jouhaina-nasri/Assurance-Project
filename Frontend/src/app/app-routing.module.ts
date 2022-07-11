import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccueilComponent } from './Pages/accueil/accueil.component';
import { AddagenceComponent } from './Pages/addagence/addagence.component';
import { AddagentComponent } from './Pages/addagent/addagent.component';
import { AddclientComponent } from './Pages/addclient/addclient.component';
import { AddvoitureComponent } from './Pages/addvoiture/addvoiture.component';
import { AgenceComponent } from './Pages/agence/agence.component';
import { AgencefermeeComponent } from './Pages/agencefermee/agencefermee.component';
import { AgentComponent } from './Pages/agent/agent.component';
import { AgentbloqueComponent } from './Pages/agentbloque/agentbloque.component';
import { CalculComponent } from './Pages/calcul/calcul.component';
import { ChercherComponent } from './Pages/chercher/chercher.component';
import { ClientComponent } from './Pages/client/client.component';
import { ClientbloqueComponent } from './Pages/clientbloque/clientbloque.component';
import { CommercialeComponent } from './Pages/commerciale/commerciale.component';
import { LoginComponent } from './login/login/login.component';
import { MainComponent } from './Pages/main/main.component';
import { MenuComponent } from './Pages/menu/menu.component';
import { ModifieragenceComponent } from './Pages/modifieragence/modifieragence.component';
import { ModifieragentComponent } from './Pages/modifieragent/modifieragent.component';
import { ModifierclientComponent } from './Pages/modifierclient/modifierclient.component';
import { ModifiervoitureComponent } from './Pages/modifiervoiture/modifiervoiture.component';
import { TaxiComponent } from './Pages/taxi/taxi.component';
import { VoitureComponent } from './Pages/voiture/voiture.component';
 

const routes: Routes = [
  { path: '', redirectTo: 'accueil', pathMatch: 'full' },
  {path: 'accueil', component: AccueilComponent}, 
  {path: 'login', component: LoginComponent}, 
  {path: 'menu', component: MenuComponent , children:[
    {path:"main",component:MainComponent},
    {path:"calcul",component:CalculComponent},
    {path:"commercial",component:CommercialeComponent},
    {path:"taxi",component:TaxiComponent},
    {path:"chercher",component:ChercherComponent},
    {path:"closeagence",component:AgencefermeeComponent},
    {path:"addagence",component:AddagenceComponent},
    {path:"modifieragence/:id",component:ModifieragenceComponent},
    {path:"agence",component:AgenceComponent},
    {path:"bloqueagent",component:AgentbloqueComponent},
    {path:"addagent",component:AddagentComponent},
    {path:"modifieragent/:id",component:ModifieragentComponent},
    {path:"agent",component:AgentComponent},
    {path:"bloqueclient",component:ClientbloqueComponent},
    {path:"addclient",component:AddclientComponent},
    {path:"modifierclient/:id",component:ModifierclientComponent},
    {path:"client",component:ClientComponent},
    {path:"addvoiture",component:AddvoitureComponent},
    {path:"modifiervoiture/:id",component:ModifiervoitureComponent},
    {path:"voiture",component:VoitureComponent}
    
  ]}
] 


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

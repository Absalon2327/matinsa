import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { inicioGuard } from '@security/guards/inicio.guard';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent,
    canActivate: [inicioGuard],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }

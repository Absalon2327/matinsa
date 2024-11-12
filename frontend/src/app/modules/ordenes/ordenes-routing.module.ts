import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrdenProduccionComponent } from './pages/orden-produccion/orden-produccion.component';
import { rutaGuard } from '@security/guards/ruta.guard';
import { NuevaOrdenComponent } from './pages/nueva-orden/nueva-orden.component';

const routes: Routes = [
  {
    path:'',
    canActivate:[rutaGuard],
    data: { expectedRol: ['PRODUCCION','ADMINISTRADOR'] },
    component:OrdenProduccionComponent
  },
  {
    path:'nuevo',
    canActivate:[rutaGuard],
    data: { expectedRol: ['PRODUCCION','ADMINISTRADOR'] },
    component:NuevaOrdenComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrdenesRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LineaProduccionComponent } from './pages/linea-produccion/linea-produccion.component';
import { rutaGuard } from '@security/guards/ruta.guard';

const routes: Routes = [
  {
    path:'',
    component:LineaProduccionComponent,
    canActivate: [rutaGuard],
    data: { expectedRol: ['ADMINISTRADOR','PRODUCCION'] },
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LineaProduccionRoutingModule { }

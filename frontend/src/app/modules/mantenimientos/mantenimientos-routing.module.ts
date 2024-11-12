import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoComponent } from './pages/producto/producto.component';
import { CategoriaComponent } from './pages/categoria/categoria.component';
import { UnidadComponent } from './pages/unidad/unidad.component';
import { ClienteComponent } from './pages/cliente/cliente.component';
import { rutaGuard } from '@security/guards/ruta.guard';

const routes: Routes = [
  {
    path: 'inventario',
    component: ProductoComponent,
    canActivate: [rutaGuard],
    data: { expectedRol: ['ADMINISTRADOR', 'BODEGA'] },
  },
  {
    path: 'categoria',
    component: CategoriaComponent,
    canActivate: [rutaGuard],
    data: { expectedRol: ['ADMINISTRADOR'] },
  },
  {
    path: 'unidad',
    component: UnidadComponent,
    canActivate: [rutaGuard],
    data: { expectedRol: ['ADMINISTRADOR'] },
  },
  {
    path: 'cliente',
    component: ClienteComponent,
    canActivate: [rutaGuard],
    data: { expectedRol: ['ADMINISTRADOR'] },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MantenimientosRoutingModule {}

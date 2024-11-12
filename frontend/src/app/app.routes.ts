import { Routes } from '@angular/router';
import { SkeletonComponent } from './layout/skeleton/skeleton.component';
import { OrdenesModule } from './modules/ordenes/ordenes.module';
import { rutaGuard } from '@security/guards/ruta.guard';
import { NotFoundComponent } from '@shared/components/not-found/not-found.component';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('@auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path:'',
    component:SkeletonComponent,
    children:[
      {
        path: 'inicio',
        loadChildren: () => import('@home/home.module').then(m => m.HomeModule)
      },
      {
        path: 'ordenes',
        loadChildren: () => import('@ordenes/ordenes.module').then(m => m.OrdenesModule),
        canActivate:[rutaGuard],
        data: { expectedRol: ['PRODUCCION','ADMINISTRADOR'] },
      },
      {
        path: 'linea-produccion',
        loadChildren: () => import('@linea-produccion/linea-produccion.module').then(m => m.LineaProduccionModule)
      },
      {
        path: 'mantenimientos',
        loadChildren: () => import('@mantenimientos/mantenimientos.module').then(m => m.MantenimientosModule)
      },
    ]
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

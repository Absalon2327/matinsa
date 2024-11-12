import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgSelectComponent, NgSelectModule } from '@ng-select/ng-select';
import { OrdenesService } from '@ordenes/services/ordenes.service';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-orden-produccion',
  standalone: true,
  imports: [
    NgxPaginationModule,
    RouterModule,
    NgSelectModule,
    FormsModule,
    AjustarTextoPipe,
  ],
  templateUrl: './orden-produccion.component.html',
  styleUrl: './orden-produccion.component.scss',
})
export class OrdenProduccionComponent {
  p = 1; // Página actual para ngx-pagination
  itemsPerPage = 5;
  estado!: string;
  fechaEsperada!: string;

  constructor(private ordenService: OrdenesService) {}
  ngOnInit(): void {
    this.getPages(0);
  }

  get listOrdenes() {
    return this.ordenService.listOrdenes;
  }

  get isLoading() {
    return this.ordenService.isLoading;
  }

  getPages(page: number) {
    this.ordenService.getOrdenesByPage(page, this.itemsPerPage);
  }

  pageChange(newPage: number) {
    this.p = newPage;
    this.getPages(this.p - 1);
  }

  generarReporte() {
    this.ordenService.generarPdf(this.estado, this.fechaEsperada).subscribe({
      next: (blob: Blob) => {
        const blobUrl = URL.createObjectURL(blob);
        window.open(blobUrl, '_blank');
      },
      error: () => {
        Swal.fire({
          title: 'Atención',
          text: 'Lo sentimos, no se pudo generar el reporte con los datos filtrados. Por favor, ajusta los filtros e inténtalo de nuevo.',
          icon: 'warning',
          buttonsStyling: true,
          confirmButtonColor: '#1d7373',
        });
      },
    });
  }

  filtrar() {
    this.p = 1;
    this.ordenService.getOrdenesByPage(
      0,
      this.itemsPerPage,
      this.estado,
      this.fechaEsperada
    );
  }
}

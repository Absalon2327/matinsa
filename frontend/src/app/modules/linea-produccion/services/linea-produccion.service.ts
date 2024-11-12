import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { environment } from '@environments/environment';
import { ILineaProduccion } from '@linea-produccion/interfaces/ILineaProduccion.interface';
import { IOrdenResponse } from '@ordenes/interfaces/IOrden.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class LineaProduccionService {
  url = `${environment.urlAPI}/linea-produccion`;
  urlOrden = `${environment.urlAPI}/orden-produccion`;
  isLoading: boolean = false;
  listOrdenes:IOrdenResponse[]=[];
  listLineasProduccion: RequestResponse<ILineaProduccion> = {
    content: [],
    pageable: {
      sort: {
        empty: true,
        unsorted: true,
        sorted: true,
      },
      offset: 0,
      pageSize: 0,
      pageNumber: 0,
      paged: true,
      unpaged: true,
    },
    last: true,
    totalPages: 0,
    totalElements: 0,
    size: 0,
    number: 0,
    sort: {
      empty: true,
      unsorted: true,
      sorted: true,
    },
    empty: true,
    first: true,
    numberOfElements: 0,
  };
  constructor(private http: HttpClient) { }

  getLineasProduccionByPage(page: number = 0, size: number = 10) {
    this.isLoading = true;
    return this.http
      .get<RequestResponse<ILineaProduccion>>(`${this.url}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe((data) => {
        this.listLineasProduccion = data;
        this.isLoading = false;
      });
  }

  getAllOrdenes() {
    return this.http.get<IOrdenResponse[]>(`${this.urlOrden}/combo`).subscribe((datos) => {
      this.listOrdenes = datos;
    });
  }

  crearLineaProduccion(datos: FormGroup){
    return this.http.post(`${this.url}`,datos);
  }

  finalizarOrden(datos: FormGroup){
    return this.http.post(`${this.url}/finalizar`,datos);
  }

}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { environment } from '@environments/environment';
import { IUnidad } from '@mantenimientos/interfaces/IProducto.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class UnidadService {

  urlUnidad = `${environment.urlAPI}/unidad`;
  isLoading: boolean = false;
  listUnidades: RequestResponse<IUnidad> = {
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

  getUnidadesByPage(page: number = 0, size: number = 10) {
    this.isLoading = true;
    return this.http
      .get<RequestResponse<IUnidad>>(`${this.urlUnidad}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe((data) => {
        this.listUnidades = data;
        this.isLoading = false;
      });
  }



  crearUnidades(datos: FormGroup){
    return this.http.post(`${this.urlUnidad}`,datos);
  }

  editarUnidades(id:number,datos: FormGroup){
    return this.http.put(`${this.urlUnidad}/${id}`,datos);
  }

  eliminarUnidades(id:number){
    return this.http.delete(`${this.urlUnidad}/${id}`);
  }


}

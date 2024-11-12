import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { environment } from '@environments/environment';
import { ICategoria } from '@mantenimientos/interfaces/IProducto.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  urlCategoria = `${environment.urlAPI}/categoria`;
  isLoading: boolean = false;
  listCategorias: RequestResponse<ICategoria> = {
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

  getCategoriasByPage(page: number = 0, size: number = 10) {
    this.isLoading = true;
    return this.http
      .get<RequestResponse<ICategoria>>(`${this.urlCategoria}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe((data) => {
        this.listCategorias = data;
        this.isLoading = false;
      });
  }



  crearCategorias(datos: FormGroup){
    return this.http.post(`${this.urlCategoria}`,datos);
  }

  editarCategorias(id:number,datos: FormGroup){
    return this.http.put(`${this.urlCategoria}/${id}`,datos);
  }

  eliminarCategorias(id:number){
    return this.http.delete(`${this.urlCategoria}/${id}`);
  }


}

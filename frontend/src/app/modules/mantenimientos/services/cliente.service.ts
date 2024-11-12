import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { environment } from '@environments/environment';
import { ICliente } from '@ordenes/interfaces/ICliente.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  urlCliente = `${environment.urlAPI}/cliente`;
  isLoading: boolean = false;
  listClientes: RequestResponse<ICliente> = {
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

  getClientesByPage(page: number = 0, size: number = 10) {
    this.isLoading = true;
    return this.http
      .get<RequestResponse<ICliente>>(`${this.urlCliente}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe((data) => {
        this.listClientes = data;
        this.isLoading = false;
      });
  }



  crearClientes(datos: FormGroup){
    return this.http.post(`${this.urlCliente}`,datos);
  }

  editarClientes(id:number,datos: FormGroup){
    return this.http.put(`${this.urlCliente}/${id}`,datos);
  }

  eliminarClientes(id:number){
    return this.http.delete(`${this.urlCliente}/${id}`);
  }
}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { environment } from '@environments/environment';
import { ICategoria, IProductoResponse, IUnidad } from '@mantenimientos/interfaces/IProducto.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  url = `${environment.urlAPI}/producto`;
  urlUnidad = `${environment.urlAPI}/unidad`;
  urlCategoria = `${environment.urlAPI}/categoria`;
  isLoading: boolean = false;
  listCategorias:ICategoria[]=[];
  listUnidades:IUnidad[]=[];
  listProductos: RequestResponse<IProductoResponse> = {
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

  getProductosByPage(page: number = 0, size: number = 10, filtro:string="1") {
    this.isLoading = true;
    return this.http
      .get<RequestResponse<IProductoResponse>>(`${this.url}/${filtro}`, {
        params: { page: page.toString(), size: size.toString() },
      })
      .subscribe((data) => {
        this.listProductos = data;
        this.isLoading = false;
      });
  }

  getAllCategorias() {
    return this.http.get<ICategoria[]>(`${this.urlCategoria}/combo`).subscribe((datos) => {
      this.listCategorias = datos;
    });
  }

  getAllUnidades() {
    return this.http.get<IUnidad[]>(`${this.urlUnidad}/combo`).subscribe((datos) => {
      this.listUnidades = datos;
    });
  }

  crearProducto(datos: FormGroup){
    return this.http.post(`${this.url}`,datos);
  }

  editarProducto(id:number,datos: FormGroup){
    return this.http.put(`${this.url}/${id}`,datos);
  }

  eliminarProducto(id:number){
    return this.http.delete(`${this.url}/${id}`);
  }

  generarPdf(tipo:string): Observable<Blob> {
    return this.http.get(this.url + `/generar-pdf/${tipo}`, { responseType: 'blob' });
  }
}

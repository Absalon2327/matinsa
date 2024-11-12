import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { IOrden, IOrdenFiltro, IOrdenResponse } from '@ordenes/interfaces/IOrden.interface';
import { ICliente } from '@ordenes/interfaces/ICliente.interface';
import { ICategoria } from '@ordenes/interfaces/ICategoria.interface';
import { ICantidad, IProducto } from '@ordenes/interfaces/IProducto.interface';
import { RequestResponse } from '@shared/interfaces/IResponse.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdenesService {

  clientes = `${environment.urlAPI}/cliente`;
  categorias = `${environment.urlAPI}/categoria`;
  productos = `${environment.urlAPI}/producto`;
  orden = `${environment.urlAPI}/orden-produccion`;
  isLoading: boolean = false;
  listClientes: ICliente[] = [];
  listCategorias: ICategoria[] = [];
  listProductos: IProducto[] = [];
  listMateriaPrima: IProducto[] = [];
  listOrdenes: RequestResponse<IOrdenFiltro> = {
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

  getAllClientes() {
    return this.http.get<ICliente[]>(`${this.clientes}/combo`).subscribe((clientes) => {
      this.listClientes = clientes;
    });
  }

  getAllCategorias() {
    return this.http.get<ICategoria[]>(`${this.categorias}/combo`).subscribe((categorias) => {
      this.listCategorias = categorias;
    });
  }

  getProductosByTipo(tipo:any) {
    return this.http.get<IProducto[]>(`${this.productos}/combo/${tipo}`).subscribe((productos) => {
      this.listProductos = productos;
    });
  }

  getAllMateriaPrima() {
    return this.http.get<IProducto[]>(`${this.productos}/combo-materia-prima`).subscribe((materiaprima) => {
      this.listMateriaPrima = materiaprima;
    });
  }

  verificarExistencias(cantidad:ICantidad){
    return this.http.post<string>(`${this.orden}/verificar-existencias`,cantidad);
  }

  crearOrdenProduccion(datos:IOrden){
    return this.http.post<string>(`${this.orden}`,datos);
  }

  getOrdenesByPage(page: number = 0, size: number = 10,estado?:string, fechaEsperada?: string) {
    this.isLoading = true;
    let params = new HttpParams();
      params = params.set('page', page.toString());
      params = params.set('size', size.toString());

    if (estado) {
      params = params.set('estado', estado.toString());
    }
    if (fechaEsperada) {
      params = params.set('fecha', fechaEsperada);
    }

    return this.http
      .get<RequestResponse<IOrdenFiltro>>(`${this.orden}/filtro`, {params})
      .subscribe((data) => {
        this.listOrdenes = data;
        this.isLoading = false;
      });
  }

  generarPdf(estado?: string, fechaEsperada?: string): Observable<Blob> {
    let params = new HttpParams();
    if (estado) {
      params = params.set('estado', estado.toString());
    }
    if (fechaEsperada) {
      params = params.set('fecha', fechaEsperada);
    }

    return this.http.get(this.orden + '/generar-pdf', { responseType: 'blob', params: params });
  }

}

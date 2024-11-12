export interface IProductoResponse {
  idProducto:number;
  estado: string;
  descripcion: string;
  codigo: string;
  cantidad: number;
  tipoProducto: string;
  nombreProducto: string;
  unidad: string;
  categoriaProducto: number;
  idUnidad: number;
  idTipoProducto: number;
}

export interface ICategoria{
  idCategoria: number;
  nombre: string;
}

export interface IUnidad{
  idUnidad:number;
  nombre:string;
}

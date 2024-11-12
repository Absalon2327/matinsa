
export interface IProducto {
  idProducto: number;
  codigo: string;
  nombreProducto: string;
  descripcion: string;
  tipoProducto: number;
  unidad: {
    idUnidad: number;
    nombre: string;
  };
  categoriaProducto: {
    idCategoria: number;
    nombre: string;
  };
  cantidad: number;
  estado: boolean;
}

export interface ICantidad{
  id:number;
  cantidad:number;
}

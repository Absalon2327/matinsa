import { IDetalleOrden } from "./IDetalleOrden.interface";

export interface IOrden{
  cliente:number;
  producto:number;
  fechaEntrega:string;
  cantidad:number;
  detallesOrden:IDetalleOrden[]
}

export interface IOrdenResponse {
  idOrden: number;
  cliente: {
    idCliente: number;
    nombre: string;
    direccion: string;
    telefono: string;
  };
  producto: {
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
  };
  fechaEntrega: string;
  cantidad: number;
  fechaFinalizacion: string | null;
  estado: string;
}


export interface IOrdenFiltro {
  idOrden: number;
    nombreCliente: string;
    fechaEntrega: string;
    fechaIngreso: string;
    fechaFinalizacion: string;
    estado: string;
    nombreProducto: string;
}

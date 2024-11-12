interface Cliente {
  idCliente: number;
  nombre: string;
  direccion: string;
  telefono: string;
}

interface Unidad {
  idUnidad: number;
  nombre: string;
}

interface CategoriaProducto {
  idCategoria: number;
  nombre: string;
}

interface Producto {
  idProducto: number;
  codigo: string;
  nombreProducto: string;
  descripcion: string;
  tipoProducto: number;
  unidad: Unidad;
  categoriaProducto: CategoriaProducto;
  cantidad: number;
  estado: boolean;
}

interface OrdenProduccion {
  idOrden: number;
  cliente: Cliente;
  producto: Producto;
  fechaEntrega: string;
  cantidad: number;
  estado: string;
}

export interface ILineaProduccion {
  idLineaProduccion: number;
  fechaIngreso: string;
  fechaFinalizacion: string | null;
  lineaProduccion: number;
  ordenProduccion: OrdenProduccion;
}

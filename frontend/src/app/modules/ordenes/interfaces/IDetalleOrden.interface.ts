import { IProducto } from "./IProducto.interface";

export interface IDetalleOrden{
  id:number;
  producto:number;
  cantidad:number;
  productoNombre:IProducto;
}

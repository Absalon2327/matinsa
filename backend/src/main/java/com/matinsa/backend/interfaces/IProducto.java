package com.matinsa.backend.interfaces;

public interface IProducto {
    Long getIdProducto();
    Long getCategoriaProducto();
    Long getIdUnidad();
    Long getIdTipoProducto();
    int getCantidad();
    String getCodigo();
    String getDescripcion();
    String getEstado();
    String getNombreProducto();
    String getTipoProducto();
    String getUnidad();
}

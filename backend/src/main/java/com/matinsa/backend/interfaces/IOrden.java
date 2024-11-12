package com.matinsa.backend.interfaces;

public interface IOrden {
    Long getIdOrden();
    String getNombreCliente();
    String getFechaEntrega();
    String getFechaIngreso();
    String getFechaFinalizacion();
    String getEstado();
    String getNombreProducto();
}

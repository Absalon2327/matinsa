package com.matinsa.backend.dto;

public record ProductoDto(
        String nombreProducto,
        String descripcion,
        int tipoProducto,
        Long unidad,
        Long categoriaProducto,
        int cantidad,
        Boolean estado
) {
}

package com.matinsa.backend.dto;

import com.matinsa.backend.enums.EstadoOrden;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record OrdenProduccionDto(
        Long cliente,
        Long producto,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaEntrega,
        int cantidad,
        EstadoOrden estado,
        List<DetalleOrdenProduccionDto> detallesOrden
) {
}

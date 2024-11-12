package com.matinsa.backend.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record FinalizarOrdenDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaFinalizacion,
        Long lineaProduccion
) {
}

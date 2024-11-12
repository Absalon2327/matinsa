package com.matinsa.backend.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record LineaProduccionDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaIngreso,
        int lineaProduccion,
        Long ordenProduccion
) {
}

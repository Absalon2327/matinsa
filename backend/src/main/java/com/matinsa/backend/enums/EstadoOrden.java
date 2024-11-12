package com.matinsa.backend.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum EstadoOrden {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    FINALIZADA("Finalizada");

    private final String descripcion;
}
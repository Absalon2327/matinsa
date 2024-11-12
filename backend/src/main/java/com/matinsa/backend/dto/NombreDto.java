package com.matinsa.backend.dto;

import jakarta.validation.constraints.Size;

public record NombreDto(@Size(max = 50, message = "El nombre debe tener como máximo 50 caracteres") String nombre) {
}

package com.matinsa.backend.security.dto;

import jakarta.validation.constraints.Email;


public record NuevoUsuario(String nombre, String nombreUsuario, @Email(message = "La dirección de email no válida") String email, String password, Long rol) {}
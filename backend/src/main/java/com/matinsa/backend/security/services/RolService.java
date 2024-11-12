package com.matinsa.backend.security.services;


import com.matinsa.backend.security.entities.Rol;

import java.util.Optional;

public interface RolService {
    Optional<Rol> getByNombreRol(String nombreRol);
    Optional<Rol> getById(Long id);

    void save(Rol rol);
}
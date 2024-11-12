package com.matinsa.backend.security.services;


import com.matinsa.backend.security.dto.JwtDto;
import com.matinsa.backend.security.dto.LoginUsuario;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.dto.NuevoUsuario;
import com.matinsa.backend.security.entities.Rol;
import com.matinsa.backend.security.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Page<Usuario> findAll(Pageable pageable);
    List<Rol> findAllRoles();

    Optional<Usuario> getByNombreUsuario(String nombreUsuario);

    Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail);

    Optional<Usuario> getByTokenPassword(String tokenPassword);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByEmail(String email);

    JwtDto login(LoginUsuario loginUsuario);

    JwtDto refresh(JwtDto jwtDto) throws ParseException;

    Mensaje save(NuevoUsuario nuevoUsuario);
    Mensaje update(Long id,NuevoUsuario nuevoUsuario);
    Mensaje delete(Long id);
}
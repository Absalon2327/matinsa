package com.matinsa.backend.services;

import com.matinsa.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICrud<T, D> {

    Page<T> listar(Pageable pageable);
    Mensaje crear(D dto);
    Mensaje editar(Long id, D dto);
    Mensaje eliminar(Long id);
    List<T> leer();
}

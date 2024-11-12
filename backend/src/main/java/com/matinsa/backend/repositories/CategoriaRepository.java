package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaProducto,Long> {

    boolean existsByNombre(String nombre);
}

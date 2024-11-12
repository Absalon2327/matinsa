package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad,Long> {

    boolean existsByNombre(String nombre);
}

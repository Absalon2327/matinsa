package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.DetalleOrdenProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenProduccion,Long> {
}

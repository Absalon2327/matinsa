package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.LineaProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaProduccionRepository extends JpaRepository<LineaProduccion,Long> {
}

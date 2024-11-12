package com.matinsa.backend.services;

import com.matinsa.backend.dto.FinalizarOrdenDto;
import com.matinsa.backend.dto.LineaProduccionDto;
import com.matinsa.backend.entities.LineaProduccion;
import com.matinsa.backend.security.dto.Mensaje;

import java.time.LocalDate;

public interface LineaProduccionService extends ICrud<LineaProduccion, LineaProduccionDto>{
    public Mensaje finalizarOrdenProduccion(FinalizarOrdenDto dto);

}

package com.matinsa.backend.services;

import com.matinsa.backend.dto.CantidadDto;
import com.matinsa.backend.dto.OrdenProduccionDto;
import com.matinsa.backend.entities.OrdenProduccion;
import com.matinsa.backend.interfaces.IOrden;
import com.matinsa.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrdenProducionService extends ICrud<OrdenProduccion, OrdenProduccionDto>{

    Mensaje verificarExistencias(CantidadDto cantidadDto);
    List<IOrden> reporte(String estado, String fecha);
    Page<IOrden> listarFiltro(String estado, String fecha, Pageable pageable);

}

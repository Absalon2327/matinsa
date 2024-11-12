package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.FinalizarOrdenDto;
import com.matinsa.backend.dto.LineaProduccionDto;
import com.matinsa.backend.entities.LineaProduccion;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.services.LineaProduccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("linea-produccion")
public class LineaProduccionController {

    private final LineaProduccionService lineaProduccionService;

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<LineaProduccion>> listar(Pageable pageable){
        return ResponseEntity.ok(lineaProduccionService.listar(pageable));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> crear(@RequestBody LineaProduccionDto dto){
        return ResponseEntity.ok(lineaProduccionService.crear(dto));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @PostMapping("/finalizar")
    public ResponseEntity<Mensaje> finalizarOrden(@RequestBody FinalizarOrdenDto dto){
        return ResponseEntity.ok(lineaProduccionService.finalizarOrdenProduccion(dto));
    }

}

package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.NombreDto;
import com.matinsa.backend.entities.Unidad;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.services.UnidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("unidad")
public class UnidadController {

    private final UnidadService unidadService;

    @GetMapping("/combo")
    public ResponseEntity<List<Unidad>> mensaje() {
        return ResponseEntity.ok(unidadService.leer());
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<Unidad>> listar(Pageable pageable) {
        return ResponseEntity.ok(unidadService.listar(pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> guardar(@RequestBody NombreDto nombreDto) {
        return ResponseEntity.ok(unidadService.crear(nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id,@RequestBody @Valid NombreDto nombreDto) {
        return ResponseEntity.ok(unidadService.editar(id,nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(unidadService.eliminar(id));
    }
}

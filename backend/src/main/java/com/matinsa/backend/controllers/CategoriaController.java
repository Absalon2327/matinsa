package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.NombreDto;
import com.matinsa.backend.entities.CategoriaProducto;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.services.CategoriaService;
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
@RequestMapping("categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/combo")
    public ResponseEntity<List<CategoriaProducto>> combo() {
        return ResponseEntity.ok(categoriaService.leer());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<CategoriaProducto>> listar(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.listar(pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> guardar(@RequestBody NombreDto nombreDto) {
        return ResponseEntity.ok(categoriaService.crear(nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id,@RequestBody @Valid NombreDto nombreDto) {
        return ResponseEntity.ok(categoriaService.editar(id,nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.eliminar(id));
    }

}

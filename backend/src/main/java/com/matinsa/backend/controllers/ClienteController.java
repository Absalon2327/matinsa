package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.ClienteDto;
import com.matinsa.backend.entities.Cliente;
import com.matinsa.backend.entities.Unidad;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.services.ClienteService;
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
@RequestMapping("cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/combo")
    public ResponseEntity<List<Cliente>> mensaje(){
        return ResponseEntity.ok(clienteService.leer());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<Cliente>> listar(Pageable pageable) {
        return ResponseEntity.ok(clienteService.listar(pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> guardar(@RequestBody ClienteDto nombreDto) {
        return ResponseEntity.ok(clienteService.crear(nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id,@RequestBody @Valid ClienteDto nombreDto) {
        return ResponseEntity.ok(clienteService.editar(id,nombreDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.eliminar(id));
    }

}

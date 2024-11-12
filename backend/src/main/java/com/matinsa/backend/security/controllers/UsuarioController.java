package com.matinsa.backend.security.controllers;


import com.matinsa.backend.security.dto.JwtDto;
import com.matinsa.backend.security.dto.LoginUsuario;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.dto.NuevoUsuario;
import com.matinsa.backend.security.entities.Rol;
import com.matinsa.backend.security.entities.Usuario;
import com.matinsa.backend.security.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping()
    public ResponseEntity<Page<Usuario>> listar(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/combo")
    public ResponseEntity<List<Rol>> roles() {
        return ResponseEntity.ok(usuarioService.findAllRoles());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/save")
    public ResponseEntity<Mensaje> save(@Valid @RequestBody NuevoUsuario nuevoUsuario) {
        return ResponseEntity.ok(usuarioService.save(nuevoUsuario));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id,@RequestBody @Valid NuevoUsuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id,usuario));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.delete(id));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario) {
        return ResponseEntity.ok(usuarioService.login(loginUsuario));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        return ResponseEntity.ok(usuarioService.refresh(jwtDto));
    }
}

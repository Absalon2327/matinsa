package com.matinsa.backend.security.servicesImpl;

import com.matinsa.backend.security.dto.JwtDto;
import com.matinsa.backend.security.dto.LoginUsuario;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.dto.NuevoUsuario;
import com.matinsa.backend.security.entities.Rol;
import com.matinsa.backend.security.entities.Usuario;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.security.jwt.JwtProvider;
import com.matinsa.backend.security.repositories.RolRepository;
import com.matinsa.backend.security.repositories.UsuarioRepository;
import com.matinsa.backend.security.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RolRepository rolRepository;

    private final JwtProvider jwtProvider;

    public Usuario findUsuarioById(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "El usuario no existe"));
    }

    public Rol findRolById(Long id){
        return rolRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el rol"));
    }


    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public List<Rol> findAllRoles(){
        return rolRepository.findAll();
    }

    @Override
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail) {
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail);
    }

    @Override
    public Optional<Usuario> getByTokenPassword(String tokenPassword) {
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public JwtDto login(LoginUsuario loginUsuario) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.nombreUsuario(), loginUsuario.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new JwtDto(jwt);
    }

    @Override
    public JwtDto refresh(JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        return new JwtDto(token);
    }

    @Override
    public Mensaje save(NuevoUsuario nuevoUsuario) {
        if (usuarioRepository.existsByNombreUsuario(nuevoUsuario.nombreUsuario()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya existe");
        if (usuarioRepository.existsByEmail(nuevoUsuario.email()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese email de usuario ya existe");

        Rol rolUsuario = findRolById(nuevoUsuario.rol());

        Usuario usuario = new Usuario(
                nuevoUsuario.nombre(),
                nuevoUsuario.nombreUsuario(),
                nuevoUsuario.email(),
                passwordEncoder.encode(nuevoUsuario.password())
        );
        usuario.setRol(rolUsuario);

        usuarioRepository.save(usuario);

        return new Mensaje(usuario.getNombreUsuario() + " ha sido creado");
    }

    @Override
    public Mensaje update(Long id, NuevoUsuario nuevoUsuario) {

        Usuario usuarioBase = findUsuarioById(id);

        if (!usuarioBase.getNombreUsuario().equals(nuevoUsuario.nombreUsuario())) {
            if (usuarioRepository.existsByNombreUsuario(nuevoUsuario.nombreUsuario())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "ese nombre de usuario ya existe");
            }
        }

        if (!usuarioBase.getEmail().equals(nuevoUsuario.email())) {
            if (usuarioRepository.existsByEmail(nuevoUsuario.email())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "ese email de usuario ya existe");
            }
        }

        Rol rolUsuario = findRolById(nuevoUsuario.rol());
        usuarioBase.setNombreUsuario(nuevoUsuario.nombreUsuario());
        usuarioBase.setEmail(nuevoUsuario.email());
        usuarioBase.setPassword(passwordEncoder.encode(nuevoUsuario.password()));
        usuarioBase.setRol(rolUsuario);
        usuarioRepository.save(usuarioBase);
        return new Mensaje(nuevoUsuario.nombreUsuario() + " ha sido editado");
    }

    @Override
    public Mensaje delete(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuarioRepository.delete(usuario);
        return new Mensaje("El usuario ha sido eliminado");
    }

}
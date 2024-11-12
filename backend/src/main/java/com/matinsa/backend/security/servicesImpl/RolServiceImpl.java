package com.matinsa.backend.security.servicesImpl;

import com.matinsa.backend.security.entities.Rol;
import com.matinsa.backend.security.repositories.RolRepository;
import com.matinsa.backend.security.services.RolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public Optional<Rol> getByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    @Override
    public Optional<Rol> getById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

}

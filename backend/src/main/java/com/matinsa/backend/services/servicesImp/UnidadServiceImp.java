package com.matinsa.backend.services.servicesImp;

import com.matinsa.backend.dto.NombreDto;
import com.matinsa.backend.entities.Unidad;
import com.matinsa.backend.repositories.UnidadRepository;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.UnidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadServiceImp implements UnidadService {

    private final UnidadRepository unidadRepository;

    private Unidad findUnidadById(Long id) {
        return unidadRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "La unidad con ID " + id + " no existe"));
    }

    @Override
    public Page<Unidad> listar(Pageable pageable) {
        return unidadRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje crear(NombreDto dto) {
        if (unidadRepository.existsByNombre(dto.nombre())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ya existe una unidad con el nombre: " + dto.nombre());
        }
        Unidad unidad = new Unidad(dto.nombre());
        unidadRepository.save(unidad);
        return new Mensaje("La unidad ha sido creada exitosamente");
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, NombreDto dto) {
        Unidad unidadBase = findUnidadById(id);
        if(!unidadBase.getNombre().equals(dto.nombre())){
            if(unidadRepository.existsByNombre(dto.nombre())){
                throw new CustomException(HttpStatus.BAD_REQUEST, "Ya existe una unidad con el nombre: " + dto.nombre());
            }
        }
        unidadBase.setNombre(dto.nombre());
        unidadRepository.save(unidadBase);
        return new Mensaje("La unidad ha sido editada exitosamente");
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        Unidad unidadBase = findUnidadById(id);
        unidadRepository.delete(unidadBase);
        return new Mensaje("La unidad ha sido eliminada exitosamente");
    }

    @Override
    public List<Unidad> leer() {
        return unidadRepository.findAll();
    }
}

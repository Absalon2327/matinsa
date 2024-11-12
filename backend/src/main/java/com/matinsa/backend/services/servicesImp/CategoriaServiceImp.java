package com.matinsa.backend.services.servicesImp;

import com.matinsa.backend.dto.NombreDto;
import com.matinsa.backend.entities.CategoriaProducto;
import com.matinsa.backend.repositories.CategoriaRepository;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImp implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private CategoriaProducto findCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "La categoría con ID " + id + " no existe"));
    }

    @Override
    public Page<CategoriaProducto> listar(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje crear(NombreDto dto) {
        if (categoriaRepository.existsByNombre(dto.nombre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ya existe una categoría con el nombre: " + dto.nombre());
        }
        CategoriaProducto categoriaProducto = new CategoriaProducto(dto.nombre());
        categoriaRepository.save(categoriaProducto);
        return new Mensaje("La categoría de producto ha sido creada exitosamente");
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, NombreDto dto) {
        CategoriaProducto unidadBase = findCategoriaById(id);
        if (!unidadBase.getNombre().equals(dto.nombre())) {
            if (categoriaRepository.existsByNombre(dto.nombre())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Ya existe una categoría con el nombre: " + dto.nombre());
            }
        }
        unidadBase.setNombre(dto.nombre());
        categoriaRepository.save(unidadBase);
        return new Mensaje("La categoría ha sido editada exitosamente");
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        CategoriaProducto categoriaProducto = findCategoriaById(id);
        categoriaRepository.delete(categoriaProducto);
        return new Mensaje("La categoría ha sido eliminada exitosamente");
    }

    @Override
    public List<CategoriaProducto> leer() {
        return categoriaRepository.findAll();
    }
}

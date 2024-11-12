package com.matinsa.backend.services;

import com.matinsa.backend.dto.ProductoDto;
import com.matinsa.backend.entities.Producto;
import com.matinsa.backend.interfaces.IProducto;
import com.matinsa.backend.security.dto.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService extends ICrud<Producto, ProductoDto>{

    List<Producto> findAllProductsByCategoria(Long id);

    List<Producto> findAllMateriaPrima();

    Page<IProducto> findAllProducts(Pageable pageable, int tipo);
    List<IProducto> reporte(Integer tipo);

}

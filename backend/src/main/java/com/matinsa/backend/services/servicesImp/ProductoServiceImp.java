package com.matinsa.backend.services.servicesImp;

import com.matinsa.backend.dto.ProductoDto;
import com.matinsa.backend.entities.CategoriaProducto;
import com.matinsa.backend.entities.Producto;
import com.matinsa.backend.entities.Unidad;
import com.matinsa.backend.interfaces.IProducto;
import com.matinsa.backend.repositories.CategoriaRepository;
import com.matinsa.backend.repositories.ProductoRepository;
import com.matinsa.backend.repositories.UnidadRepository;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UnidadRepository unidadRepository;
    private final CategoriaRepository categoriaRepository;
    private static final String PRODUCTO_TERMINADO_PREFIJO = "PT";
    private static final String MATERIA_PRIMA_PREFIJO = "MP";
    public static final int PRODUCTO_TERMINADO = 1;
    public static final int PRODUCTO_MATERIA_PRIMA = 2;

    private Producto findProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "El producto con ID " + id + " no existe"));
    }

    private Unidad findUnidadById(Long id) {
        return unidadRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "La unidad con ID " + id + " no existe"));
    }

    private CategoriaProducto findCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "La categoría con ID " + id + " no existe"));
    }

    @Override
    public Page<Producto> listar(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje crear(ProductoDto dto) {
        if (productoRepository.existsByNombreProducto(dto.nombreProducto())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ya existe una producto con el nombre: " + dto.nombreProducto());
        }
        String codigo = generarCodigo(dto.tipoProducto());
        Unidad unidad = findUnidadById(dto.unidad());
        Producto producto;
        if(dto.tipoProducto()==PRODUCTO_TERMINADO){
            CategoriaProducto categoria = findCategoriaById(dto.categoriaProducto());
            producto = new Producto(codigo, dto.nombreProducto(), dto.descripcion(), dto.tipoProducto(), unidad, categoria, dto.cantidad());
        }else {
            producto = new Producto(codigo, dto.nombreProducto(), dto.descripcion(), dto.tipoProducto(), unidad, dto.cantidad());
        }

        productoRepository.save(producto);
        return new Mensaje("El producto ha sido creado exitosamente");
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, ProductoDto dto) {
        Producto producto = findProductoById(id);
        producto.setDescripcion(dto.descripcion());
        producto.setCantidad(dto.cantidad());
        producto.setEstado(dto.estado());
        return new Mensaje("El producto ha sido editado exitosamente");
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        Producto producto = findProductoById(id);
        productoRepository.delete(producto);
        return new Mensaje("El producto ha sido eliminado exitosamente");
    }

    @Override
    public List<Producto> leer() {
        return productoRepository.findAllByEstado(Boolean.TRUE);
    }

    @Override
    public List<Producto> findAllProductsByCategoria(Long id) {
        return productoRepository.findAllProductsByCategoriaAndEstado(id,Boolean.TRUE);
    }

    @Override
    public List<Producto> findAllMateriaPrima() {
        return productoRepository.findAllMateriaPrima();
    }

    @Override
    public Page<IProducto> findAllProducts(Pageable pageable,int tipo) {
        return productoRepository.findAllProducts(pageable, tipo);
    }

    @Override
    public List<IProducto> reporte(Integer tipo) {
        return productoRepository.findAllProducts(tipo);
    }


    private String generarCodigo(int tipoProducto) {
        Long correlativo = productoRepository.getCorrelativo(verificarTipoProducto(tipoProducto));
        if (tipoProducto == PRODUCTO_TERMINADO) {
            return PRODUCTO_TERMINADO_PREFIJO + (correlativo + 1);
        } else {
            return MATERIA_PRIMA_PREFIJO + (correlativo + 1);
        }
    }



    private int verificarTipoProducto(int tipoProducto) {
        if (tipoProducto == PRODUCTO_TERMINADO || tipoProducto == PRODUCTO_MATERIA_PRIMA) {
            return tipoProducto;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Lo sentimos, el tipo de producto seleccionado no existe en nuestro sistema. Por favor, asegúrate de ingresar un valor válido. Las opciones válidas son: 1 - Producto terminado, 2 - Matería prima.");
        }
    }


}

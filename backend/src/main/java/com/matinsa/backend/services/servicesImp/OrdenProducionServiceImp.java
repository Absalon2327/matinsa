package com.matinsa.backend.services.servicesImp;

import com.matinsa.backend.dto.*;
import com.matinsa.backend.entities.*;
import com.matinsa.backend.enums.EstadoOrden;
import com.matinsa.backend.interfaces.IOrden;
import com.matinsa.backend.repositories.*;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.OrdenProducionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrdenProducionServiceImp implements OrdenProducionService {

    private final OrdenProduccionRepository ordenRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public static final int TERMINADO = 1;
    public static final int MATERIA_PRIMA = 2;

    private OrdenProduccion findOrdenById(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "La orden de producción con ID " + id + " no existe"));
    }

    private Cliente findClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "El cliente con ID " + id + " no existe"));
    }

    private Producto findProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "El producto con ID " + id + " no existe"));
    }

    @Override
    public Page<OrdenProduccion> listar(Pageable pageable) {
        return ordenRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje crear(OrdenProduccionDto dto) {
        Cliente cliente = findClienteById(dto.cliente());
        Producto producto = comprobarTipoProducto(dto.producto(), TERMINADO);
        OrdenProduccion ordenProduccion = new OrdenProduccion(cliente, producto, dto.fechaEntrega(), dto.cantidad());
        Set<DetalleOrdenProduccion> detallesOrden = new HashSet<>();

        for (DetalleOrdenProduccionDto detalle : dto.detallesOrden()) {
            Producto materiaPrima = comprobarTipoProductoyExistencia(detalle.producto(), MATERIA_PRIMA, detalle.cantidad());
            DetalleOrdenProduccion nuevoDetalle = new DetalleOrdenProduccion(ordenProduccion, materiaPrima, detalle.cantidad());
            materiaPrima.setCantidad(materiaPrima.getCantidad() - detalle.cantidad());
            detallesOrden.add(nuevoDetalle);
        }
        ordenProduccion.setDetallesOrden(detallesOrden);
        ordenRepository.save(ordenProduccion);
        return new Mensaje("La Orden de producción se ha creado exitosamente");
    }

    @Override
    public Mensaje editar(Long id, OrdenProduccionDto dto) {
        return null;
    }

    @Override
    public Mensaje eliminar(Long id) {
        return null;
    }

    @Override
    public List<OrdenProduccion> leer() {
        return ordenRepository.findAllByEstado(EstadoOrden.PENDIENTE);
    }

    @Override
    public Mensaje verificarExistencias(CantidadDto cantidadDto) {
        return verificarExistencia(cantidadDto.id(),MATERIA_PRIMA,cantidadDto.cantidad());
    }

    @Override
    public List<IOrden> reporte(String estado, String fecha) {
        return ordenRepository.reporte(estado,fecha);
    }

    @Override
    public Page<IOrden> listarFiltro(String estado, String fecha, Pageable pageable) {
        return ordenRepository.listar(estado,fecha, pageable);
    }


    private Producto comprobarTipoProducto(Long id, int tipo) {
        Producto producto = findProductoById(id);
        if (producto.getTipoProducto() == tipo) {
            return producto;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "El producto " + producto.getNombreProducto()+ (tipo == TERMINADO ? " seleccionado en la orden de producción" : " seleccionado en el detalle de la orden") + " no es " + (tipo == TERMINADO ? "un producto terminado" : "materia prima"));
        }
    }

    private Producto comprobarTipoProductoyExistencia(Long id, int tipo, int cantidad) {
        Producto producto = comprobarTipoProducto(id, tipo);
        if (producto.getCantidad() >= cantidad) {
            return producto;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Lo sentimos, actualmente no contamos con suficientes existencias de "+ producto.getNombreProducto() +" en nuestro inventario.");
        }
    }

    private Mensaje verificarExistencia(Long id, int tipo, int cantidad){
        Producto producto = comprobarTipoProducto(id, tipo);
        if (producto.getCantidad() >= cantidad) {
            return new Mensaje("Existencias suficientes");
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Lo sentimos, actualmente no contamos con suficientes existencias de "+ producto.getNombreProducto() +" en nuestro inventario.");
        }
    }
}

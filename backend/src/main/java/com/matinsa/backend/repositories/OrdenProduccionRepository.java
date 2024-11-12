package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.OrdenProduccion;
import com.matinsa.backend.enums.EstadoOrden;
import com.matinsa.backend.interfaces.IOrden;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion,Long> {
    List<OrdenProduccion> findAllByEstado(EstadoOrden estado);

    @Query(value = "SELECT o.id_orden as idOrden, c.nombre as nombreCliente, TO_CHAR(o.fecha_entrega, 'DD/MM/YYYY')  as fechaEntrega, TO_CHAR(l.fecha_ingreso, 'DD/MM/YYYY') as fechaIngreso, TO_CHAR(l.fecha_finalizacion, 'DD/MM/YYYY') as fechaFinalizacion, o.estado, p.nombre_producto as nombreProducto FROM orden_produccion o LEFT JOIN linea_produccion l ON l.id_orden_produccion = o.id_orden INNER JOIN cliente c ON c.id_cliente = o.id_cliente inner join producto p on p.id_producto = o.id_producto where (:estado IS NULL OR o.estado = :estado) AND (:fecha IS NULL OR DATE(o.fecha_entrega) = CAST(:fecha AS DATE))",nativeQuery = true)
    List<IOrden> reporte(@PathParam("estado") String estado, @PathParam("fecha") String fecha);

    @Query(value = "SELECT o.id_orden as idOrden, C.nombre as nombreCliente, TO_CHAR(o.fecha_entrega, 'DD/MM/YYYY')  as fechaEntrega, TO_CHAR(l.fecha_ingreso, 'DD/MM/YYYY') as fechaIngreso, TO_CHAR(l.fecha_finalizacion, 'DD/MM/YYYY') as fechaFinalizacion, o.estado, p.nombre_producto as nombreProducto FROM orden_produccion o LEFT JOIN linea_produccion l ON l.id_orden_produccion = o.id_orden INNER JOIN cliente c ON c.id_cliente = o.id_cliente inner join producto p on p.id_producto = o.id_producto where (:estado IS NULL OR o.estado = :estado) AND (:fecha IS NULL OR DATE(o.fecha_entrega) = CAST(:fecha AS DATE))",nativeQuery = true)
    Page<IOrden> listar(@PathParam("estado") String estado, @PathParam("fecha") String fecha, Pageable pageable);
}

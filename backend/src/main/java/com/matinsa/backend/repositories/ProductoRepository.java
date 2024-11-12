package com.matinsa.backend.repositories;

import com.matinsa.backend.entities.Producto;
import com.matinsa.backend.interfaces.IProducto;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Query(value = "SELECT count(id_producto) from producto WHERE tipo_producto=:tipo",nativeQuery = true)
    Long getCorrelativo(@PathParam("tipo") int tipo);

    boolean existsByNombreProducto(String nombre);

    @Query(value = "SELECT * from producto WHERE id_categoria_producto=:categoria and estado=:estado and tipo_producto=1",nativeQuery = true)
    List<Producto> findAllProductsByCategoriaAndEstado(@PathParam("categoria") Long categoria,@PathParam("estado") Boolean estado);

    @Query(value = "SELECT * from producto WHERE tipo_producto=2",nativeQuery = true)
    List<Producto> findAllMateriaPrima();

    @Query(value = "SELECT p.id_producto as idProducto, p.id_unidad as idUnidad, p.tipo_producto as idTipoProducto, p.cantidad, p.codigo, p.descripcion,p.id_categoria_producto as categoriaProducto, CASE WHEN p.estado = TRUE THEN 'Activo' WHEN p.estado = FALSE THEN 'Inactivo' END as estado, p.nombre_producto as nombreProducto, CASE WHEN p.tipo_producto = 1 THEN 'Producto terminado' WHEN p.tipo_producto = 2 THEN 'Materia prima' ELSE 'Otro tipo' END as tipoProducto, u.nombre as unidad FROM producto p INNER JOIN unidad u ON u.id_unidad = p.id_unidad WHERE (:tipo IS NULL OR p.tipo_producto = :tipo)", nativeQuery = true)
    Page<IProducto> findAllProducts(Pageable pageable, @PathParam("tipo") int tipo);

    @Query(value = "SELECT p.id_producto as idProducto, p.id_unidad as idUnidad, p.tipo_producto as idTipoProducto, p.cantidad, p.codigo, p.descripcion,p.id_categoria_producto as categoriaProducto, CASE WHEN p.estado = TRUE THEN 'Activo' WHEN p.estado = FALSE THEN 'Inactivo' END as estado, p.nombre_producto as nombreProducto, CASE WHEN p.tipo_producto = 1 THEN 'Producto terminado' WHEN p.tipo_producto = 2 THEN 'Materia prima' ELSE 'Otro tipo' END as tipoProducto, u.nombre as unidad FROM producto p INNER JOIN unidad u ON u.id_unidad = p.id_unidad WHERE (:tipo IS NULL OR p.tipo_producto = :tipo)", nativeQuery = true)
    List<IProducto> findAllProducts(@PathParam("tipo") Integer tipo);

    List<Producto> findAllByEstado(Boolean estado);

}

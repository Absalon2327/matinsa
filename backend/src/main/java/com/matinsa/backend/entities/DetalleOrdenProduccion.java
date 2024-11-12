package com.matinsa.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_orden_produccion")
public class DetalleOrdenProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_orden")
    private Long idDetalleOrden;

    @ManyToOne
    @JoinColumn(name = "id_orden_produccion")
    private OrdenProduccion ordenProduccion;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    private int cantidad;

    public DetalleOrdenProduccion(OrdenProduccion ordenProduccion, Producto producto, int cantidad) {
        this.ordenProduccion = ordenProduccion;
        this.producto = producto;
        this.cantidad = cantidad;
    }
}

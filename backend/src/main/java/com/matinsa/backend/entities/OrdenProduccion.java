package com.matinsa.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matinsa.backend.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orden_produccion")
public class OrdenProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long idOrden;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    private int cantidad;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado= EstadoOrden.PENDIENTE;

    @JsonIgnore
    @OneToMany(mappedBy = "ordenProduccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetalleOrdenProduccion> detallesOrden =new HashSet<>();

    public OrdenProduccion(Cliente cliente, Producto producto, LocalDate fechaEntrega, int cantidad) {
//        this.detallesOrden=
        this.cliente = cliente;
        this.producto = producto;
        this.fechaEntrega = fechaEntrega;
        this.cantidad = cantidad;
    }
}

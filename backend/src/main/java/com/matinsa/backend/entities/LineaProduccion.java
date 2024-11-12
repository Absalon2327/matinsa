package com.matinsa.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linea_produccion")
public class LineaProduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea_produccion")
    private Long idLineaProduccion;

    @Column(name = "fecha_Ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @Column(name = "linea_produccion")
    private int lineaProduccion;

    @ManyToOne
    @JoinColumn(name = "id_orden_produccion")
    private OrdenProduccion ordenProduccion;

    public LineaProduccion(LocalDate fechaIngreso, int lineaProduccion, OrdenProduccion ordenProduccion) {
        this.fechaIngreso = fechaIngreso;
        this.lineaProduccion = lineaProduccion;
        this.ordenProduccion = ordenProduccion;
    }
}

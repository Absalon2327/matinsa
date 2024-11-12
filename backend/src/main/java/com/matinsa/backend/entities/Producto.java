package com.matinsa.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    @Column(unique = true)
    private String codigo;
    @Column(name = "nombre_producto", unique = true)
    private String nombreProducto;
    private String descripcion;
    @Column(name = "tipo_producto")
    private int tipoProducto;

    @ManyToOne
    @JoinColumn(name = "id_unidad")
    private Unidad unidad;

    @ManyToOne
    @JoinColumn(name = "id_categoria_producto")
    private CategoriaProducto categoriaProducto;

    private int cantidad;

    private Boolean estado = Boolean.TRUE;

    public Producto(String codigo, String nombreProducto, String descripcion, int tipoProducto, Unidad unidad, CategoriaProducto categoriaProducto, int cantidad) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.unidad = unidad;
        this.categoriaProducto = categoriaProducto;
        this.cantidad = cantidad;
    }

    public Producto(String codigo, String nombreProducto, String descripcion, int tipoProducto, Unidad unidad, int cantidad) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }
}

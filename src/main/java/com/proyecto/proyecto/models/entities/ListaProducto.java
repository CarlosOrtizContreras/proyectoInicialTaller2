package com.proyecto.proyecto.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ListaProducto implements Serializable {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Long totalPrdoducto;
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    
    public ListaProducto(Long totalPrdoducto, int cantidad, Factura factura, Producto producto) {
        this.totalPrdoducto = totalPrdoducto;
        this.cantidad = cantidad;
        this.factura = factura;
        this.producto = producto;
    }

    // serializacion
    public static long getSerialversionuid() {
        return serialversionUID;
    }

    private static final long serialversionUID = 1L;

}

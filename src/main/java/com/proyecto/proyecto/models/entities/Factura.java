package com.proyecto.proyecto.models.entities;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Factura implements Serializable {
    @Id
    @Column(unique = true)
    private int id;
    private Double total;
    private LocalDate fechaCompra;
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "nitEmpresa", nullable = false)
    private Empresa empresa;


    //serializacion
    public static long getSerialversionuid() {
        return serialversionUID;
    }

    private static final long serialversionUID = 1L;

}

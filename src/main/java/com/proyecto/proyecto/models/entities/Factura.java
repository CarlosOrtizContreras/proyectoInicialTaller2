package com.proyecto.proyecto.models.entities;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Factura implements Serializable {
    @Id
    @Column(unique = true)
    private int id;
    private Double total;
    @CreatedDate
    private LocalDateTime fechaCompra;
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "nitEmpresa", nullable = false)
    private Empresa empresa;

    

    public Factura(int id, Double total, Cliente cliente, Empresa empresa) {
        this.id = id;
        this.total = total;
        this.cliente = cliente;
        this.empresa = empresa;
    }

    
    //serializacion
    public static long getSerialversionuid() {
        return serialversionUID;
    }

    private static final long serialversionUID = 1L;

}

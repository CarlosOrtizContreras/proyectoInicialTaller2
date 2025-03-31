package com.proyecto.proyecto.models.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
@EntityListeners(AuditingEntityListener.class)
public class Producto implements Serializable {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int stock;
    private int precio;

    @LastModifiedDate
    private LocalDate fecha;

    
    public Producto() {
    }
     
    public Producto(int id, String nombre, int stock, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;

    }
    public Producto(String nombre, int stock, int precio) {
       
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
      
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public static long getSerialversionuid() {
        return serialversionUID;
    }

    private static final long serialversionUID = 1L;
    
}

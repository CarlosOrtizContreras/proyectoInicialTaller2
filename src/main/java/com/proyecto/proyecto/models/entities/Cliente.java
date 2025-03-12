package com.proyecto.proyecto.models.entities;

import java.io.Serializable;
import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity

public class Cliente implements Serializable {
    @Id
    @Column(unique = true )
    private int id;
    private String nombre,  email, primerapellido, segundoapellido;

    @Temporal(TemporalType.DATE)
    private LocalDate fecha;


   
    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public Cliente(int id, String nombre, String email, String primerapellido, String segundoapellido, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.primerapellido = primerapellido;
        this.segundoapellido = segundoapellido;
        this.fecha = fecha;
    }

    public Cliente() {
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


    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

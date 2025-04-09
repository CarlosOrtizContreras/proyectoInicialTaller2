package com.proyecto.proyecto.models.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

    @Id
    @Column(unique = true )
    private int id;
    private String nombre,  email, primerapellido, segundoapellido,contraseña,rol;

   @CreatedDate
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

    public Cliente(int id, String nombre, String email, String primerapellido, String segundoapellido, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.primerapellido = primerapellido;
        this.segundoapellido = segundoapellido;
        this.contraseña = contraseña;
        this.rol = "0";
    }
    
    public Cliente(int id, String nombre, String email, String primerapellido, String segundoapellido,
            String contraseña, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.primerapellido = primerapellido;
        this.segundoapellido = segundoapellido;
        this.contraseña = contraseña;
        this.fecha = fecha;
        this.rol = "0";
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


    
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

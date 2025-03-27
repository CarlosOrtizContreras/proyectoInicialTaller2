package com.proyecto.proyecto.models.entities;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Empresa implements Serializable {

    @Id
    @Column(unique = true)
    private int nit;
    private String nombre;
    private String descripcion;


    //serializacion
    public static long getSerialversionuid() {
        return serialversionUID;
    }

    private static final long serialversionUID = 1L;


}

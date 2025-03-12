package com.proyecto.proyecto.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.models.entities.Producto;
@Repository
public interface IDaoProducto extends JpaRepository<Producto, Integer>{
    
}

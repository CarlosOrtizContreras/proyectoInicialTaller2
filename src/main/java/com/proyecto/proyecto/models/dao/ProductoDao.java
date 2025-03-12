package com.proyecto.proyecto.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.models.entities.Producto;

@Service 
public class ProductoDao  {

    @Autowired
    private IDaoProducto daoProducto;

    public List<Producto> listar (){
        return daoProducto.findAll();
    }
    
    
}

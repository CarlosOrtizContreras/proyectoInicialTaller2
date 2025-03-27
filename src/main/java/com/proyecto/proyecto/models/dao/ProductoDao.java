package com.proyecto.proyecto.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.proyecto.proyecto.models.entities.Producto;

@Service 
public class ProductoDao  {

    @Autowired
    private RepositorioProducto daoProducto;

    public List<Producto> listar (){
        return daoProducto.findAll();
    }
    
    public void eliminarProducto(int id) {
        daoProducto.deleteById(id);
    }
    
    public Boolean encontrarProducto(int id) {
        return daoProducto.existsById(id);}
    

    public void crear (Producto producto){
        daoProducto.save(producto);
    }
     public Optional<Producto> buscarProducto(int id){
        return daoProducto.findById(id);    
    }
       public Producto obtenerProducto(int id){
        return buscarProducto(id).get();
    }
    
}

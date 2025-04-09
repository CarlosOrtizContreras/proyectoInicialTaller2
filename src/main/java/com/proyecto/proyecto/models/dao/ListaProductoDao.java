package com.proyecto.proyecto.models.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.models.entities.ListaProducto;

@Service
public class ListaProductoDao {
    @Autowired
    private RepositorioListaProducto repositorioListaProducto;

    public void guardar(ListaProducto listaProducto) {
        repositorioListaProducto.save(listaProducto);
    }

    public boolean buscarListaPorProductoyFactura(int idProducto, int idFactura) {
        return repositorioListaProducto.existsByProductoIdAndFacturaId(idProducto, idFactura);
    }

    public void eliminarListaPorProductoyFactura(int idProducto, int idFactura) {
        
        repositorioListaProducto.deleteById(repositorioListaProducto.findByProductoIdAndFacturaId(idProducto, idFactura).getId());
    }
    

    public ListaProducto obtenerListaPorProdcutoyFactura(int idProducto, int idFactura) {
        return repositorioListaProducto.findByProductoIdAndFacturaId(idProducto, idFactura);
    }

    public ArrayList<ListaProducto> obtenerTodoListaPorFactura(int idFactura) {
        return repositorioListaProducto.findAllByFacturaId(idFactura);
    }

}
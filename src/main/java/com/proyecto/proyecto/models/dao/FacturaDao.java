package com.proyecto.proyecto.models.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.proyecto.models.entities.Factura;

public class FacturaDao {
    @Autowired
    private RepositorioFactura repositorioFactura;

    public Boolean encontrarIdFactura (int id){
        return repositorioFactura.existsById(id);
    }

    public void guardarFactura(Factura factura){
        repositorioFactura.save(factura);
    }
}

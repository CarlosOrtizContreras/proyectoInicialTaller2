package com.proyecto.proyecto.models.dao;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.models.entities.Factura;

@Service
public class FacturaDao {
    @Autowired
    private RepositorioFactura repositorioFactura;

    public Boolean encontrarIdFactura(int id) {
        return repositorioFactura.existsById(id);
    }

    public void guardarFactura(Factura factura) {
        repositorioFactura.save(factura);
    }

    public Optional<Factura> buscarFactura(int id) {
        return repositorioFactura.findById(id);
    }

    public Factura obtenerFactura(int id) {
        return buscarFactura(id).get();
    }

    public List<Factura> listarFacturas() {
        return repositorioFactura.findAll();
    }
}

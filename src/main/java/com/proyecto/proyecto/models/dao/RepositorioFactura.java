package com.proyecto.proyecto.models.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.models.entities.Cliente;
import com.proyecto.proyecto.models.entities.Factura;

@Repository
public interface RepositorioFactura extends JpaRepository <Factura, Integer>{

public ArrayList<Factura> findByCliente(Cliente cliente);
    
}
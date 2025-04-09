package com.proyecto.proyecto.models.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.models.entities.ListaProducto;

@Repository
public interface RepositorioListaProducto extends JpaRepository<ListaProducto, Integer> {

    public boolean existsByProductoIdAndFacturaId(int Producto, int Factura);

    public void deleteByProductoIdAndFacturaId(int Producto, int Factura);

    public ListaProducto findByProductoIdAndFacturaId(int Producto, int Factura);

    public ArrayList<ListaProducto> findAllByFacturaId(int Factura);
}

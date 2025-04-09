package com.proyecto.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.proyecto.models.dao.FacturaDao;
import com.proyecto.proyecto.models.dao.ListaProductoDao;
import com.proyecto.proyecto.models.dao.ProductoDao;
import com.proyecto.proyecto.models.entities.ListaProducto;
import com.proyecto.proyecto.models.entities.Producto;

@RequestMapping("/listaProducto")
@Controller
public class ControllerListaProducto {

    @Autowired
    private ListaProductoDao listaProductoDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;
    
    @PostMapping("/guardarListaProducto")
    public String guardarListaProducto(@RequestParam("idFactura") int idFactura, @RequestParam("idProducto")int idProducto, @RequestParam("cantidad") int cantidad,
            RedirectAttributes redirectAtributes){
        int precio = productoDao.obtenerProducto(idProducto).getPrecio();
        int cantidadFinal=actualizarCantidadProducto(idProducto, cantidad);

        if (cantidadFinal > 0) {
            Long precioTotal = (long) precio * cantidadFinal;
            ListaProducto listaProducto;
            if (listaProductoDao.buscarListaPorProductoyFactura(idProducto, idFactura)) {
                listaProducto = new ListaProducto(
                        precioTotal + listaProductoDao.obtenerListaPorProdcutoyFactura(idProducto, idFactura)
                                .getTotalPrdoducto(),
                        cantidadFinal
                                + listaProductoDao.obtenerListaPorProdcutoyFactura(idProducto, idFactura).getCantidad(),
                        facturaDao.obtenerFactura(idFactura),
                        productoDao.obtenerProducto(idProducto));
                listaProductoDao.eliminarListaPorProductoyFactura(idProducto, idFactura);
                listaProductoDao.guardar(listaProducto);
            } else {
                listaProducto = new ListaProducto(precioTotal, cantidadFinal, facturaDao.obtenerFactura(idFactura),
                        productoDao.obtenerProducto(idProducto));
                listaProductoDao.guardar(listaProducto);
            }
        }
        if (cantidadFinal ==cantidad && cantidadFinal != 0){
            redirectAtributes.addFlashAttribute("mensaje", "Agregado al carrito de compra");
        }else if (cantidadFinal< cantidad){
            redirectAtributes.addFlashAttribute("mensaje", "Agregado al carrito de compra "+ cantidadFinal + " de "+ cantidad);

        }else{
            redirectAtributes.addFlashAttribute("mensaje", "No hay productos en Stock o no has seleccionado producto a agregar");

        }
       return "redirect:/producto/listarComprar?idFactura="+idFactura;
    }

    public int actualizarCantidadProducto(int idProducto, int cantidad){
        Producto producto = productoDao.obtenerProducto(idProducto);
        if(producto.getStock()>= cantidad && cantidad >0){
            
            producto.setStock(producto.getStock()-cantidad);
            productoDao.crear(producto);
        }else if(producto.getStock() < cantidad && cantidad > 0){
           
            cantidad = producto.getStock();
            producto.setStock( 0);
            productoDao.crear(producto);
        }else{
            cantidad = 0;
        }
        return cantidad;
    }
}

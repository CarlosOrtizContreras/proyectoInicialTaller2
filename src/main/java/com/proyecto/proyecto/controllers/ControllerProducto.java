package com.proyecto.proyecto.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.proyecto.models.dao.ListaProductoDao;
import com.proyecto.proyecto.models.dao.ProductoDao;
import com.proyecto.proyecto.models.entities.Producto;


@Controller
@RequestMapping("/producto")
public class ControllerProducto {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private ListaProductoDao listaProductoDao;
    
    @GetMapping("/listar")
    public String listar(Model model, RedirectAttributes redirectAttributes) {
    
        if (productoDao.listar().isEmpty()) {
            redirectAttributes.addFlashAttribute("productosNoRegistrados","NO SE ENCUENTRAN PRODUCTOS REGISTRADOS");
            return "redirect:/cliente/menuProducto" ;
        } else {
            model.addAttribute("Titulo", "Lista de Productos");
            model.addAttribute("producto", productoDao.listar());

            return "/templatesProducto/ListarProducto";
        }
    }
    
    @GetMapping("/listarComprar")
    public String listarComprar(@RequestParam int idFactura ,Model model, RedirectAttributes redirectAttributes) {

        if (productoDao.listar().isEmpty()) {
            redirectAttributes.addFlashAttribute("productosNoRegistrados", "NO SE ENCUENTRAN PRODUCTOS REGISTRADOS");

            return "redirect:/cliente/inicioCliente" ;
        } else {
            model.addAttribute("Titulo", "Lista de Productos");
            model.addAttribute("producto", productoDao.listar());
            model.addAttribute("idFactura", idFactura);

            return "/templatesProducto/ListarProductoComprar";
        }
    }
    



    @PostMapping("/realizarEliminacion")
    public String realizarEliminacion(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
            if(!listaProductoDao.encontrarProductoComprado(id)){
                  productoDao.eliminarProducto(id);
            return "redirect:/producto/listar";
            }else{
                redirectAttributes.addFlashAttribute("productoActivo","EL PRODUCTO SE ENCUENTRA ACTIVO EN EL INVENTARIO");
                return "redirect:/producto/listar" ;
            }
          
    
    }

     @GetMapping("/ingresar")

    public String ingresar(Model model) {

        return "/templatesProducto/IngresarProducto";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precio") int precio
    )
            {
       
        
            Producto producto = new Producto(nombre,stock,precio);
            productoDao.crear(producto);
            return "redirect:/producto/listar";
        

    }

    


    
    @GetMapping("/actualizar")
    public String actualizar() {
        return "/templatesProducto/ActualizarProducto";
    }

    @PostMapping("/realizarVerificacion")
    public String realizarVerificacion(@RequestParam int id, Model model) {
       
            model.addAttribute("producto", productoDao.obtenerProducto(id));
            return "/templatesProducto/ActualizarProductoDatos";
      
    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(
            @RequestParam("id")  int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precio") int precio)
             {
     
      
            Producto producto = new Producto(id,nombre,stock,precio);
            productoDao.crear(producto);
            return "redirect:/producto/listar";
        

    }


    
}

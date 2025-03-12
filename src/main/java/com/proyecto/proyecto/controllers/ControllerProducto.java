package com.proyecto.proyecto.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.proyecto.models.dao.ProductoDao;
import com.proyecto.proyecto.models.entities.Cliente;
import com.proyecto.proyecto.models.entities.Producto;


@Controller
@RequestMapping("/producto")
public class ControllerProducto {

    @Autowired
    private ProductoDao productoDao;
    
    @GetMapping("/listar")
    public String listar(Model model) {
    
        if (productoDao.listar().isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN PRODUCTOS REGISTRADOS";
        } else {
            model.addAttribute("Titulo", "Lista de Productos");
            model.addAttribute("producto", productoDao.listar());

            return "ListarProducto";
        }
    }
    

       @GetMapping("/eliminar")
    public String eliminar() {
        return "EliminarProducto";
    }

    @PostMapping("/realizarEliminacion")
    public String realizarEliminacion(@RequestParam("id") int id) {

        if (productoDao.encontrarProducto(id)) {
            productoDao.eliminarProducto(id);
            return "redirect:/cliente/mensaje?mensaje=" + "EL PRODUCTO FUE ELIMINADO CON EXITO";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL PRODUCTO NO SE ENCUENTRA REGISTRADO";
        }

    }

     @GetMapping("/ingresar")

    public String ingresar(Model model) {

        return "IngresarProducto";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precio") int precio,
            @RequestParam("fecha") String fecha) {
        LocalDate fechaP = LocalDate.parse(fecha);
        
            Producto producto = new Producto(nombre,stock,precio, fechaP);
            productoDao.crear(producto);
            return "redirect:/producto/listar";
        

    }

    
    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "BuscarProducto";
    }

    @PostMapping("/realizarBusqueda")
    public String realizarBusqueda(@RequestParam("id") int id) {

        return "redirect:/producto/listarBusqueda?id=" + id;
    }

    @GetMapping("/listarBusqueda")
    public String listarBusqueda(@RequestParam int id, Model model) {
        List<Producto> producto = new ArrayList<>();
        Optional<Producto> resultado = productoDao.buscarProducto(id);
        if (resultado.isPresent()) {
            producto.add(resultado.get());
            model.addAttribute("Titulo", "Lista de Productos");
            model.addAttribute("producto", producto);

            return "ListarProducto";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL PRODUCTO NO SE ENCUENTRA REGISTRADO";
        }

    }

    
    @GetMapping("/actualizar")
    public String actualizar() {
        return "ActualizarProducto";
    }

    @PostMapping("/realizarVerificacion")
    public String realizarVerificacion(@RequestParam int id, Model model) {
        if (productoDao.encontrarProducto(id)) {
            model.addAttribute("producto", productoDao.obtenerProducto(id));
            return "ActualizarProductoDatos";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL PRODUCTO NO SE ENCUENTRA REGISTRADO";
        }

    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(
            @RequestParam("id")  int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precio") int precio,
            @RequestParam("fecha") String fecha) {
        LocalDate fechaP = LocalDate.parse(fecha);
      
            Producto producto = new Producto(id,nombre,stock,precio, fechaP);
            productoDao.crear(producto);
            return "redirect:/producto/listarBusqueda?id=" + id;
        

    }


    
}

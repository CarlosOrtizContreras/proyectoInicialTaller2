package com.proyecto.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


import com.proyecto.proyecto.models.dao.ProductoDao;


@Controller
@RequestMapping("/producto")
public class ControllerProducto {

    @Autowired
    private ProductoDao productoDao;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("Titulo", "Lista  de Productos");
        model.addAttribute("producto", productoDao.listar());
        return "ListarProducto";
    }

    
    
}

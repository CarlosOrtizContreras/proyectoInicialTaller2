package com.proyecto.proyecto.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.proyecto.models.dao.EmpresaDao;

import com.proyecto.proyecto.models.entities.Empresa;

@Controller
@RequestMapping("/empresa")


public class ControllerEmpresa {
    
    @Autowired
    private EmpresaDao empresaDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        if (empresaDao.listarEmpresa().isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN EMPRESAS REGISTRADAS";
        } else {
            model.addAttribute("Titulo", "Lista de Empresas");
            model.addAttribute("empresa", empresaDao.listarEmpresa());

            return "/templatesEmpresa/ListarEmpresa";
        }

    }

    @GetMapping("/ingresar")

    public String ingresar(Model model) {

        return "/templatesEmpresa/IngresarEmpresa";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("nit") int nit,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion
           ) {
    
        if (empresaDao.encontrarEmpresa(nit)) {
            return "redirect:/empresa/listarBusqueda?nit=" + nit;

        
        } else {
            Empresa empresa = new Empresa(nit, nombre, descripcion);
            empresaDao.crearEmpresa(empresa);
            return "redirect:/empresa/listarBusqueda?nit=" + nit;
        }

    }

    @GetMapping("/buscar")
    public String buscar() {
        return "/templatesEmpresa/BuscarEmpresa";
    }

    @PostMapping("/realizarBusqueda")
    public String realizarBusqueda(@RequestParam("nit") int nit) {

        return "redirect:/empresa/listarBusqueda?nit=" + nit;
    }

    @GetMapping("/listarBusqueda")
    public String listarBusqueda(@RequestParam int nit, Model model) {
        List<Empresa> empresa = new ArrayList<>();
        Optional<Empresa> resultado = empresaDao.buscarEmpresa(nit);
        if (resultado.isPresent()) {
            empresa.add(resultado.get());
            model.addAttribute("Titulo", "Lista de Empresas");
            model.addAttribute("empresa", empresa);

            return "/templatesEmpresa/ListarEmpresa";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "LA EMPRESA NO SE ENCUENTRA REGISTRADO";
        }

    }

   
    @GetMapping("/eliminar")
    public String eliminar() {
        return "/templatesEmpresa/EliminarEmpresa";
    }

    @PostMapping("/realizarEliminacion")
    public String realizarEliminacion(@RequestParam("nit") int nit) {

        if (empresaDao.encontrarEmpresa(nit)) {
            empresaDao.eliminarEmpresa(nit);
            return "redirect:/cliente/mensaje?mensaje=" + "LA EMPRESA FUE ELIMINADO CON EXITO";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + " LA EMPRESA NO SE ENCUENTRA REGISTRADO";
        }

    }



    @GetMapping("/actualizar")
    public String actualizar() {
        return "/templatesEmpresa/ActualizarEmpresa";
    }

    @PostMapping("/realizarVerificacion")
    public String realizarVerificacion(@RequestParam("nit") int nit, Model model) {
        if (empresaDao.encontrarEmpresa(nit)) {
            model.addAttribute("empresa", empresaDao.obtenerEmpresa(nit));
            return "/templatesEmpresa/ActualizarEmpresaDatos";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "LA EMPRESA NO SE ENCUENTRA REGISTRADO";
        }

    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(@RequestParam("nit") int nit,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion
          
           ) {
            Empresa empresa = new Empresa(nit, nombre, descripcion);
            empresaDao.crearEmpresa(empresa);
            return "redirect:/empresa/listarBusqueda?nit=" + nit;
        

    }
}

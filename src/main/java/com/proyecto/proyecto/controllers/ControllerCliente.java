package com.proyecto.proyecto.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.proyecto.proyecto.models.dao.ClienteDao;
import com.proyecto.proyecto.models.entities.Cliente;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping({"/cliente","/"})
public class ControllerCliente {

    @Autowired
    private ClienteDao clienteDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        if (clienteDao.listarClientes().isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN CLIENTES REGISTRADOS";
        } else {
            model.addAttribute("Titulo", "Lista de Clientes");
            model.addAttribute("cliente", clienteDao.listarClientes());

            return "/templatesCliente/ListarCliente";
        }

    }

    @GetMapping("/ingresar")

    public String ingresar(Model model) {

        return "/templatesCliente/IngresarCliente";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("primerapellido") String primerApellido,
            @RequestParam("segundoapellido") String segundoApellido, @RequestParam("email") String email
           ) {
    
        if (clienteDao.encontrarCliente(id)) {
            return "redirect:/cliente/listarBusqueda?id=" + id;

        
        } else {
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido);
            clienteDao.crear(cliente);
            return "redirect:/cliente/listarBusqueda?id=" + id;
        }

    }

    @GetMapping("/buscar")
    public String buscar() {
        return "/templatesCliente/BuscarCliente";
    }

    @PostMapping("/realizarBusqueda")
    public String realizarBusqueda(@RequestParam("id") int id) {

        return "redirect:/cliente/listarBusqueda?id=" + id;
    }

    @GetMapping("/listarBusqueda")
    public String listarBusqueda(@RequestParam int id, Model model) {
        List<Cliente> cliente = new ArrayList<>();
        Optional<Cliente> resultado = clienteDao.buscarCliente(id);
        if (resultado.isPresent()) {
            cliente.add(resultado.get());
            model.addAttribute("Titulo", "Lista de Clientes");
            model.addAttribute("cliente", cliente);

            return "/templatesCliente/ListarCliente";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE NO SE ENCUENTRA REGISTRADO";
        }

    }

    @GetMapping("/menuCliente")
    public String menuCliente() {

        return "MenuCliente";
    }

    @GetMapping("/menuProducto")
    public String menuProducto() {

        return "MenuProducto";
    }
    
    @GetMapping("/menuEmpresa")
    public String menuEmpresa() {

        return "MenuEmpresa";
    }

    @GetMapping("/")
    public String inicio() {
        return "Inicio";
    }
    

    @GetMapping("/eliminar")
    public String eliminar() {
        return "/templatesCliente/EliminarCliente";
    }

    @PostMapping("/realizarEliminacion")
    public String realizarEliminacion(@RequestParam("id") int id) {

        if (clienteDao.encontrarCliente(id)) {
            clienteDao.eliminarCliente(id);
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE FUE ELIMINADO CON EXITO";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE NO SE ENCUENTRA REGISTRADO";
        }

    }

    @GetMapping("/mensaje")
    public String mensaje(@RequestParam String mensaje, Model model) {
        model.addAttribute("Mensaje", mensaje);
        return "Mensaje";
    }

    @GetMapping("/actualizar")
    public String actualizar() {
        return "/templatesCliente/ActualizarCliente";
    }

    @PostMapping("/realizarVerificacion")
    public String realizarVerificacion(@RequestParam int id, Model model) {
        if (clienteDao.encontrarCliente(id)) {
            model.addAttribute("Cliente", clienteDao.obtenerCliente(id));
            return "/templatesCliente/ActualizarClienteDatos";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE NO SE ENCUENTRA REGISTRADO";
        }

    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(@RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("primerapellido") String primerApellido,
            @RequestParam("segundoapellido") String segundoApellido, @RequestParam("email") String email
           ) {
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido);
            clienteDao.crear(cliente);
            return "redirect:/cliente/listarBusqueda?id=" + id;
        

    }


}

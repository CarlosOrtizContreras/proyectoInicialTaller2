package com.proyecto.proyecto.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/cliente")
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

            return "ListarCliente";
        }

    }

    @GetMapping("/ingresar")

    public String ingresar(Model model) {

        return "IngresarCliente";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("primerapellido") String primerApellido,
            @RequestParam("segundoapellido") String segundoApellido, @RequestParam("email") String email,
            @RequestParam("fecha") String fecha) {
        LocalDate fechaP = LocalDate.parse(fecha);
        if (clienteDao.encontrarCliente(id)) {
            return "redirect:/cliente/listarBusqueda?id=" + id;

        
        } else {
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido, fechaP);
            clienteDao.crear(cliente);
            return "redirect:/cliente/listarBusqueda?id=" + id;
        }

    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "BuscarCliente";
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

            return "ListarCliente";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE NO SE ENCUENTRA REGISTRADO";
        }

    }

    @GetMapping("/menu")
    public String menu() {

        return "Menu";
    }

    @GetMapping("/eliminar")
    public String eliminar() {
        return "EliminarCliente";
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
        return "ActualizarCliente";
    }

    @PostMapping("/realizarVerificacion")
    public String realizarVerificacion(@RequestParam int id, Model model) {
        if (clienteDao.encontrarCliente(id)) {
            model.addAttribute("Cliente", clienteDao.obtenerCliente(id));
            return "ActualizarClienteDatos";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE NO SE ENCUENTRA REGISTRADO";
        }

    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(@RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("primerapellido") String primerApellido,
            @RequestParam("segundoapellido") String segundoApellido, @RequestParam("email") String email,
            @RequestParam("fecha") String fecha) {
        LocalDate fechaP = LocalDate.parse(fecha);
      
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido, fechaP);
            clienteDao.crear(cliente);
            return "redirect:/cliente/listarBusqueda?id=" + id;
        

    }


}

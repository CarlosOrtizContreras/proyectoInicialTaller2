package com.proyecto.proyecto.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.proyecto.proyecto.models.dao.ClienteDao;
import com.proyecto.proyecto.models.dao.FacturaDao;
import com.proyecto.proyecto.models.entities.Cliente;
import com.proyecto.proyecto.models.entities.Factura;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping({"/cliente","/"})
public class ControllerCliente {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private FacturaDao facturaDao;

    @PostMapping("/validarLogIn")
    public String realizarBusqueda(@RequestParam("id") int id, @RequestParam("contraseña") String contraseña, Model model, RedirectAttributes redirectAttributes) {
        if(clienteDao.encontrarCliente(id)){
            Optional<Cliente> clienteSesion = clienteDao.buscarCliente(id);
            if(clienteSesion.get().getContraseña().equals(contraseña)){
                if (clienteSesion.get().getRol().equals("0")) {
                    return "redirect:/cliente/inicioCliente?id=" + id; 
                }else{
                    return "InicioAdmin";
                }
                
            }
            model.addAttribute("error", "Usuario o Contraseña Incorrectas");
            return "InicioSesion";
        }else{
            model.addAttribute("error", "Usuario o Contraseña Incorrectas");
            return "InicioSesion";
        }
       
        
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        if (clienteDao.listarClientes().isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN CLIENTES REGISTRADOS";
        } else {
            ArrayList<Cliente> soloCliente = new ArrayList<>();
            for(Cliente e: clienteDao.listarClientes()){
                if(e.getRol().equals("0")){
                    soloCliente.add(e);
                }
            }
            model.addAttribute("Titulo", "Lista de Clientes");
            model.addAttribute("cliente", soloCliente);

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
            @RequestParam("segundoapellido") String segundoApellido,
            @RequestParam("contraseña") String contraseña, @RequestParam("email") String email
            ,RedirectAttributes redirectAtributes
            ) {
    
        if (clienteDao.encontrarCliente(id)) {
            redirectAtributes.addFlashAttribute("estadoProceso", "EL USUARIO YA SE ENCUENTRA REGISTRADO");
            return "redirect:/";

        
        } else {
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido,contraseña);
            clienteDao.crear(cliente);
            redirectAtributes.addFlashAttribute("estadoProceso", "EL USUARIO FUE REGISTRADO CON EXITO");

            return "redirect:/";
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

    @GetMapping("/inicioCliente")
    public String menuCliente( @RequestParam("id") Integer id, Model model) {
        model.addAttribute("idCliente", id);
        return "InicioCliente";
    }

    @GetMapping("/actualizacionContrasena")
    public String actualizacionContraseña(){
        return "/templatesCliente/ActualizarContrasena";
    }

    @GetMapping("/inicioAdmin")
    public String inicioAdmin() {

        return "InicioAdmin";
    }

    @GetMapping("/menuProducto")
    public String menuProducto() {

        return "MenuProducto";
    }
    
    @GetMapping("/menuCliente")
    public String menuClienteAdmin() {

        return "MenuCliente";
    }
    
    @GetMapping("/menuEmpresa")
    public String menuEmpresa() {

        return "MenuEmpresa";
    }
    @GetMapping("/menuFactura")
    public String menuFactura() {

        return "MenuFactura";
    }

    @GetMapping("/")
    public String inicioSesion(){
        return "InicioSesion";
    }
   

    @GetMapping("/eliminar")
    public String eliminar() {
        return "/templatesCliente/EliminarCliente";
    }

    @PostMapping("/realizarEliminacion")
    public String realizarEliminacion(@RequestParam("id") int id) {
             ArrayList<Factura> lista = facturaDao.listarFacturasPorId(id);
         if (!lista.isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + " SE ENCUENTRAN FACTURAS REGISTRADAS POR EL USUARIO, NO SE PUEDE ELIMINAR";}
            else {
                clienteDao.eliminarCliente(id);
                return "redirect:/cliente/mensaje?mensaje=" + "EL CLIENTE FUE ELIMINADO CON EXITO";
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
    
            model.addAttribute("Cliente", clienteDao.obtenerCliente(id));
            return "/templatesCliente/ActualizarClienteDatos";
       

    }
    @PostMapping ("/realizarActualizacion")
    public String realizarctualizacion(@RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("primerapellido") String primerApellido,
            @RequestParam("segundoapellido") String segundoApellido,
           
            @RequestParam("email") String email
           ) {
            Cliente cliente = new Cliente(id, nombre, email, primerApellido, segundoApellido, clienteDao.obtenerCliente(id).getContraseña(),clienteDao.obtenerCliente(id).getFecha());
            clienteDao.crear(cliente);
            return "redirect:/cliente/listar";
        

    }

    @PostMapping("/realizarActualizacionContrasena")
    public String realizarctualizacionContraseña(@RequestParam("id") int id,
            @RequestParam("contraseña") String contraseña, RedirectAttributes redirectAttributes) {
                if(clienteDao.encontrarCliente(id)){
                    Cliente viejo = clienteDao.obtenerCliente(id);
                    Cliente cliente = new Cliente(id, viejo.getNombre(), viejo.getEmail(), viejo.getPrimerapellido(), viejo.getSegundoapellido(),
                            contraseña, viejo.getFecha());
                    clienteDao.crear(cliente);
                    redirectAttributes.addFlashAttribute("estadoActualizacion", "CONTRASEÑA ACTUALIZADA");
                    return "redirect:/";

                }else{
                    redirectAttributes.addFlashAttribute("estadoActualizacion", "EL USUARIO NO ESTA REGISTRADO");
                    return "redirect:/";
                }
        
        

    }


}

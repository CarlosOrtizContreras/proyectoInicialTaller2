package com.proyecto.proyecto.controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.proyecto.models.dao.ClienteDao;
import com.proyecto.proyecto.models.dao.EmpresaDao;
import com.proyecto.proyecto.models.dao.FacturaDao;
import com.proyecto.proyecto.models.entities.Cliente;
import com.proyecto.proyecto.models.entities.Empresa;
import com.proyecto.proyecto.models.entities.Factura;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/factura")
@Controller
public class ControllerFactura {
    
    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private EmpresaDao empresaDao;

    @PostMapping("/verificarUsuario")
    public String verificarUsuario(@RequestParam("id")int id) {
        if( clienteDao.encontrarCliente(id)){
            int idFactura = generarIdFactura(clienteDao.obtenerCliente(id));
            return "redirect:/producto/listarComprar";
        }else{
            return "/templatesCliente/IngresarCliente";
        }
         
    }   
    
    @GetMapping("/buscarClienteFactura")
    public String buscarUsuarioFactura() {
        return "templatesFactura/BuscarClienteFactura";
    }
    private  int generarIdFactura(Cliente cliente) {
        Random random = new Random();
        int idFactura = 0; 
        boolean habilitador  = false;
        Empresa empresa = empresaDao.obtenerEmpresa(800157253);
        while (!habilitador) {
            idFactura = random.nextInt(0,100000);
            if(!facturaDao.encontrarIdFactura(idFactura)){
                Factura factura = new Factura(idFactura,0D, cliente, empresa);
                facturaDao.guardarFactura(factura);
                habilitador = true;
            }
        } 
        return idFactura ;
    }
}

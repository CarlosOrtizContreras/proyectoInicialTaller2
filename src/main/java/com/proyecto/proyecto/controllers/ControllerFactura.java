package com.proyecto.proyecto.controllers;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.proyecto.models.dao.ClienteDao;
import com.proyecto.proyecto.models.dao.EmpresaDao;
import com.proyecto.proyecto.models.dao.FacturaDao;
import com.proyecto.proyecto.models.dao.ListaProductoDao;
import com.proyecto.proyecto.models.entities.Cliente;
import com.proyecto.proyecto.models.entities.Empresa;
import com.proyecto.proyecto.models.entities.Factura;
import com.proyecto.proyecto.models.entities.ListaProducto;

import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("/factura")
@Controller
public class ControllerFactura {
    @Autowired
    private ListaProductoDao listaProductoDao;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private EmpresaDao empresaDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        if (facturaDao.listarFacturas().isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN FACTURAS REGISTRADAS";
        } else {
            model.addAttribute("Titulo", "Lista de Facturas");
            model.addAttribute("factura", facturaDao.listarFacturas());

            return "/templatesFactura/ListarFactura";
        }

    }

    @GetMapping("/buscar")
    public String buscar() {
        return "/templatesFactura/BuscarFactura";
    }

    @PostMapping("/realizarBusqueda")
    public String realizarBusqueda(@RequestParam("id") int id) {

        return "redirect:/factura/listarBusqueda?id=" + id;
    }

    @GetMapping("/listarBusqueda")
    public String listarBusqueda(@RequestParam int id, Model model) {
        List<Factura> factura = new ArrayList<>();
        Optional<Factura> resultado = facturaDao.buscarFactura(id);
        if (resultado.isPresent()) {
            factura.add(resultado.get());
            model.addAttribute("Titulo", "Lista de Facturas");
            model.addAttribute("factura", factura);

            return "/templatesFactura/ListarFactura";
        } else {
            return "redirect:/cliente/mensaje?mensaje=" + "LA EMPRESA NO SE ENCUENTRA REGISTRADO";
        }

    }

    @PostMapping("/verificarUsuario")
    public String verificarUsuario(@RequestParam("id") int id) {
        if (clienteDao.encontrarCliente(id)) {
            int idFactura = generarIdFactura(clienteDao.obtenerCliente(id));
            return "redirect:/producto/listarComprar?idFactura=" + idFactura;
        } else {
            return "/templatesCliente/IngresarCliente";
        }

    }

    @GetMapping("/buscarClienteFactura")
    public String buscarUsuarioFactura() {
        return "templatesFactura/BuscarClienteFactura";
    }

    private int generarIdFactura(Cliente cliente) {
        Random random = new Random();
        int idFactura = 0;
        boolean habilitador = false;
        Empresa empresa = empresaDao.obtenerEmpresa(800157253);
        while (!habilitador) {
            idFactura = random.nextInt(0, 100000);
            if (!facturaDao.encontrarIdFactura(idFactura)) {
                Factura factura = new Factura(idFactura, 0L, cliente, empresa);
                facturaDao.guardarFactura(factura);
                habilitador = true;
            }
        }
        return idFactura;
    }

    @PostMapping("/listarFactura")
    public String listarFacttura(@RequestParam("idFactura") int idFactura, Model model) {
        Factura factura = facturaDao.obtenerFactura(idFactura);
        ArrayList<ListaProducto> lista = listaProductoDao.obtenerTodoListaPorFactura(idFactura);
        Long totalCompra = 0L;
        for (ListaProducto e : lista) {
            totalCompra = totalCompra + e.getTotalPrdoducto();
        }
        factura.setTotal(totalCompra);
        facturaDao.guardarFactura(factura);
        model.addAttribute("empresa", factura.getEmpresa());
        model.addAttribute("cliente", factura.getCliente());
        model.addAttribute("lista", lista);
        model.addAttribute("totalCompra", totalCompra);
        model.addAttribute("fechaCompra", factura.getFechaCompra());
        model.addAttribute("idFactura", factura.getId());

        return "templatesFactura/Factura";
    }

}

package com.proyecto.proyecto.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
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
    @PostMapping("/listarPorId")
    public String listarPorId(@RequestParam("id") int id, Model model) {
         ArrayList<Factura> lista = facturaDao.listarFacturasPorId(id);
        if (lista.isEmpty()) {
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE ENCUENTRAN FACTURAS REGISTRADAS";
        } else {
            model.addAttribute("Titulo", "Lista de Facturas");
            model.addAttribute("factura", lista);

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
            return "redirect:/cliente/mensaje?mensaje=" + "LA FACTURA NO SE ENCUENTRA REGISTRADA";
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
    public String listarFactura(@RequestParam("idFactura") int idFactura, Model model) {
        Factura factura = facturaDao.obtenerFactura(idFactura);
        ArrayList<ListaProducto> lista = listaProductoDao.obtenerTodoListaPorFactura(idFactura);
        Long totalCompra = 0L;
        for (ListaProducto e : lista) {
            totalCompra = totalCompra + e.getTotalPrdoducto();
        }
        factura.setTotal(totalCompra);
        if (totalCompra <= 0) {
            facturaDao.eliminarFactura(idFactura);
            return "redirect:/cliente/mensaje?mensaje=" + "NO SE REALIZO NINGUNA COMPRA";

        } else {
            facturaDao.guardarFactura(factura);

            model.addAttribute("empresa", factura.getEmpresa());
            model.addAttribute("cliente", factura.getCliente());
            model.addAttribute("lista", lista);
            String totalString = String.format("%,d", totalCompra);
            model.addAttribute("totalCompra", totalString);
            model.addAttribute("fechaCompra", factura.getFechaCompra());
            model.addAttribute("idFactura", factura.getId());

            return "templatesFactura/Factura";
        }

    }

    @PostMapping("/pdf")
    public ResponseEntity<InputStreamResource> pdf(@RequestParam("idFactura") int idFactura) {
        ByteArrayInputStream bis = generarFacturaPdf(idFactura);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }

    private ByteArrayInputStream generarFacturaPdf(int idFactura) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("FACTURA DE COMPRA", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Factura factura = facturaDao.obtenerFactura(idFactura);
            ArrayList<ListaProducto> lista = listaProductoDao.obtenerTodoListaPorFactura(idFactura);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Codigo de la Factura: " + factura.getId()));
            document.add(new Paragraph("NIT: " + factura.getEmpresa().getNit()));
            document.add(new Paragraph("Empresa: " + factura.getEmpresa().getNombre()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Identificacion Cliente: " + factura.getCliente().getId()));
            document.add(new Paragraph("Nombre Cliente: " + factura.getCliente().getNombre() + " "
                    + factura.getCliente().getPrimerapellido() + " " + factura.getCliente().getSegundoapellido()));
            document.add(new Paragraph("Fecha: " + factura.getFechaCompra()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[] { 2, 4, 3, 3 });
            Stream.of("Cantidad", "Nombre", "Precio", "Precio Total").forEach(header -> {
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPhrase(new Phrase(header));
                table.addCell(cell);
            });
            String precio;
            for (ListaProducto e : lista) {

                table.addCell(String.valueOf(e.getCantidad()));
                table.addCell(e.getProducto().getNombre());
                precio = String.format("%,d", e.getProducto().getPrecio());
                table.addCell(precio);
                precio = String.format("%,d", e.getTotalPrdoducto());
                table.addCell(precio);

            }
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            String totalFactura = String.format("%,d", factura.getTotal());
            document.add(new Paragraph("Total de la Compra: $" + totalFactura, font));
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());

    }
}

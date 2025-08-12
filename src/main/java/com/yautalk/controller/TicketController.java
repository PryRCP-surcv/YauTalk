package com.yautalk.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yautalk.model.Respuesta;
import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;
import com.yautalk.service.RespuestaService;
import com.yautalk.service.TicketService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cliente")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RespuestaService respuestaService;


    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @GetMapping("/crear-ticket")
    public String mostrarFormularioCrearTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "crear-ticket";
    }

    @PostMapping("/crear-ticket")
    public String procesarFormularioTicket(@ModelAttribute("ticket") Ticket ticket,
                                           @RequestParam("archivo") MultipartFile archivo,
                                           RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        ticket.setCliente(usuario);
        ticket.setFechaCreacion(LocalDate.now());
        ticket.setEstado("Pendiente");

        if (ticket.getCategoria() != null && ticket.getCategoria().isBlank()) {
            ticket.setCategoria(null);
        }

        if (ticket.getPrioridad() != null && ticket.getPrioridad().isBlank()) {
            ticket.setPrioridad(null);
        }

        // Guardar archivo en carpeta local "uploads"
        if (!archivo.isEmpty()) {
            try {
                String nombreArchivo = archivo.getOriginalFilename();
                String rutaDirectorio = new File("uploads").getAbsolutePath();
                File directorio = new File(rutaDirectorio);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                File archivoDestino = new File(rutaDirectorio + File.separator + nombreArchivo);
                archivo.transferTo(archivoDestino);
                ticket.setNombreArchivoAdjunto(nombreArchivo);
            } catch (IOException | IllegalStateException e) {
                logger.error("Error al guardar archivo adjunto", e);
            }
        }

        logger.info("DATOS DEL TICKET >>>");
        logger.info("Título: " + ticket.getTitulo());
        logger.info("Descripción: " + ticket.getDescripcion());
        logger.info("Categoría: " + ticket.getCategoria());
        logger.info("Prioridad: " + ticket.getPrioridad());
        logger.info("Cliente ID: " + ticket.getCliente().getIdUsuario());

        ticketService.guardarTicket(ticket);
        redirectAttributes.addFlashAttribute("mensaje", "Ticket creado exitosamente.");

        return "redirect:/cliente/mis-tickets";
    }


    @GetMapping("/mis-tickets")
public String verMisTickets(Model model, HttpSession session) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Usuario cliente = (Usuario) auth.getPrincipal();

    List<Ticket> tickets = ticketService.obtenerTicketsPorCliente(cliente);
    model.addAttribute("tickets", tickets);

    Map<Long, Respuesta> respuestas = new HashMap<>();
    for (Ticket ticket : tickets) {
        Respuesta respuesta = respuestaService.obtenerPorTicket(ticket);
        if (respuesta != null) {
            respuestas.put(ticket.getId(), respuesta);
        }
    }
    model.addAttribute("respuestas", respuestas);

    // Mostrar resaltado solo una vez
    Boolean mostrado = (Boolean) session.getAttribute("resaltadoMostrado");
    if (mostrado == null || !mostrado) {
        model.addAttribute("primeravez", true);
        session.setAttribute("resaltadoMostrado", true);
    } else {
        model.addAttribute("primeravez", false);
    }

    return "mis-tickets";
}

}

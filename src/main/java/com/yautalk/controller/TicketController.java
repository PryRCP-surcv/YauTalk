package com.yautalk.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;
import com.yautalk.service.TicketService;

@Controller
@RequestMapping("/cliente")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @GetMapping("/crear-ticket")
    public String mostrarFormularioCrearTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "crear-ticket";
    }

    @PostMapping("/crear-ticket")
    public String procesarFormularioTicket(@ModelAttribute("ticket") Ticket ticket,
            @RequestParam("archivo") MultipartFile archivo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        ticket.setCliente(usuario);
        ticket.setFechaCreacion(LocalDate.now());
        ticket.setEstado("Pendiente");

        if (!archivo.isEmpty()) {
            try {
                String nombreArchivo = archivo.getOriginalFilename();
                String rutaDirectorio = new File("src/main/resources/static/uploads").getAbsolutePath();
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

        // LOG de verificación ANTES de guardar
        logger.info("DATOS DEL TICKET >>>");
        logger.info("Título: " + ticket.getTitulo());
        logger.info("Descripción: " + ticket.getDescripcion());
        logger.info("Categoría: " + ticket.getCategoria());
        logger.info("Prioridad: " + ticket.getPrioridad());
        logger.info("Cliente ID: " + ticket.getCliente().getIdUsuario());

        ticketService.guardarTicket(ticket);

        return "redirect:/cliente/mis-tickets";
    }

    @GetMapping("/mis-tickets")
    public String verMisTickets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        List<Ticket> tickets = ticketService.obtenerTicketsPorCliente(usuario);
        model.addAttribute("tickets", tickets);

        return "mis-tickets";
    }

}

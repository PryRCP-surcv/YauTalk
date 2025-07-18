package com.yautalk.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yautalk.model.Respuesta;
import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;
import com.yautalk.repository.RespuestaRepository;
import com.yautalk.service.TicketService;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping("/inicio")
    public String mostrarInicioTecnico(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario tecnico = (Usuario) auth.getPrincipal();
        model.addAttribute("usuario", tecnico);
        return "tecnico-inicio";
    }

    @GetMapping("/tickets-disponibles")
    public String verTicketsDisponibles(Model model) {
        List<Ticket> pendientes = ticketService.obtenerTicketsPendientes();
        model.addAttribute("tickets", pendientes);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario tecnico = (Usuario) auth.getPrincipal();
        model.addAttribute("usuario", tecnico);

        return "tickets-disponibles"; // nombre del HTML
    }

    @GetMapping("/atender-ticket/{id}")
    public String atenderTicket(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.obtenerTicketPorId(id);
        if (ticket == null || !ticket.getEstado().equals("Pendiente")) {
            return "redirect:/tecnico/tickets-disponibles";
        }

        // Cambiar estado a "En Proceso"
        ticket.setEstado("En Proceso");
        ticketService.guardarTicket(ticket);

        // Obtener técnico autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario tecnico = (Usuario) auth.getPrincipal();
        model.addAttribute("usuario", tecnico);

        model.addAttribute("ticket", ticket);
        return "atender-ticket";
    }

    @PostMapping("/resolver-ticket/{id}")
public String resolverTicket(@PathVariable Long id,
                             @RequestParam("respuesta") String contenido) {

    Ticket ticket = ticketService.obtenerTicketPorId(id);
    if (ticket == null || !ticket.getEstado().equals("En Proceso")) {
        return "redirect:/tecnico/tickets-disponibles";
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Usuario tecnico = (Usuario) auth.getPrincipal();

    // Crear y guardar la respuesta
    Respuesta respuesta = new Respuesta();
    respuesta.setContenido(contenido);
    respuesta.setFecha(LocalDate.now());
    respuesta.setTicket(ticket);
    respuesta.setTecnico(tecnico);
    respuestaRepository.save(respuesta);

    // Cambiar estado del ticket a resuelto
    ticket.setEstado("Resuelto (Remoto)"); // o "Resuelto (Presencial)" si luego agregas esa opción
    ticketService.guardarTicket(ticket);

    return "redirect:/tecnico/mis-tickets";

}

@GetMapping("/mis-tickets")
public String verMisTickets(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Usuario tecnico = (Usuario) auth.getPrincipal();

    List<Ticket> misTickets = ticketService.obtenerTicketsPorTecnico(tecnico);
    model.addAttribute("tickets", misTickets);
    model.addAttribute("usuario", tecnico);

    return "tecnico-mis-tickets";
}
}

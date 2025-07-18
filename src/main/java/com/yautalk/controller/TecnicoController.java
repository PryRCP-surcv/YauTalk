package com.yautalk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;
import com.yautalk.service.TicketService;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {

    @Autowired
    private TicketService ticketService;

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

    

}

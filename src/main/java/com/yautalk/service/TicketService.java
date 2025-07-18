package com.yautalk.service;

import java.util.List;

import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;

public interface TicketService {
    Ticket guardarTicket(Ticket ticket);
    List<Ticket> listarTicketsPorCliente(Long idCliente); // para futuras fases
    List<Ticket> obtenerTicketsPorCliente(Usuario cliente);

    List<Ticket> obtenerTicketsPendientes(); // Método nuevo

    Ticket obtenerTicketPorId(Long id);// obtener por id de ticket



}

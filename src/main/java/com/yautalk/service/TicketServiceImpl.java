package com.yautalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;
import com.yautalk.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket guardarTicket(Ticket ticket) {
        System.out.println("TICKET A GUARDAR: " + ticket.getTitulo());
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> listarTicketsPorCliente(Long idCliente) {
        return ticketRepository.findAll(); // Se puede personalizar más adelante
    }

    @Override
    public List<Ticket> obtenerTicketsPorCliente(Usuario cliente) {
        return ticketRepository.findByCliente(cliente);
    }

}

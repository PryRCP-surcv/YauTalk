package com.yautalk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yautalk.model.Ticket;
import com.yautalk.model.Usuario;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCliente(Usuario cliente);

    List<Ticket> findByEstado(String estado);

    // Puedes agregar métodos personalizados más adelante si es necesario
}

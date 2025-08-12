package com.yautalk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yautalk.model.Respuesta;
import com.yautalk.model.Ticket;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    
    // Opcional: si quieres recuperar todas las respuestas de un ticket
    List<Respuesta> findByTicket(Ticket ticket);
    
}

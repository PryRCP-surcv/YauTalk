package com.yautalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yautalk.model.Respuesta;
import com.yautalk.model.Ticket;
import com.yautalk.repository.RespuestaRepository;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    public Respuesta obtenerPorTicket(Ticket ticket) {
        return respuestaRepository.findByTicket(ticket)
                .stream()
                .findFirst()
                .orElse(null);
    }
}

package com.yautalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping("/inicio")
    public String inicioCliente() {
        return "cliente-inicio"; // Retorna la vista cliente-inicio.html
    }
}

package com.yautalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {

    @GetMapping("/inicio")
    public String inicioTecnico() {
        return "tecnico-inicio"; 
    }
}

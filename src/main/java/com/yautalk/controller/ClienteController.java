package com.yautalk.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yautalk.model.Usuario;
import com.yautalk.service.UsuarioService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/inicio")
    public String inicioCliente(Principal principal, Model model) {
        // Obtener el correo del usuario autenticado
        String correo = principal.getName();

        // Buscar al usuario en la base de datos
        Usuario usuario = usuarioService.obtenerPorCorreo(correo);

        // Pasar el usuario a la vista
        model.addAttribute("usuario", usuario);

        return "cliente-inicio"; // nombre de la vista HTML
    }
}

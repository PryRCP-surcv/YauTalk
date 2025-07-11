package com.yautalk.service;

import java.util.List;

import com.yautalk.model.Usuario;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);
    Usuario obtenerPorCorreo(String correo);
    Usuario obtenerPorNombreUsuario(String nombreUsuario);
    //  login por correo o nombre de usuario
    Usuario obtenerPorCorreoONombreUsuario(String input);

    List<Usuario> listarUsuarios();
}

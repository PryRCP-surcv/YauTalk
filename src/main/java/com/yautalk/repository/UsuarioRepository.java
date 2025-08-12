package com.yautalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yautalk.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // permite buscar por ambos
    Optional<Usuario> findByCorreoOrNombreUsuario(String correo, String nombreUsuario);
}

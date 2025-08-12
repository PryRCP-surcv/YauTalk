package com.yautalk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yautalk.dto.UsuarioDTO;
import com.yautalk.model.Rol;
import com.yautalk.model.Usuario;
import com.yautalk.repository.RolRepository;
import com.yautalk.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/test")
    public String test() {
        return "El backend responde correctamente.";
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String password) {
        return passwordEncoder.encode(password);
    }

    // Este es el POST correcto con validación
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // Encriptar
        usuario.setTelefono1(usuarioDTO.getTelefono1());
        usuario.setTelefono2(usuarioDTO.getTelefono2());
        usuario.setTelefono3(usuarioDTO.getTelefono3());

        Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no válido"));
        usuario.setRol(rol);

        Usuario creado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(creado);
    }
}

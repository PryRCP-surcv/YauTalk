package com.yautalk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yautalk.model.Rol;
import com.yautalk.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol obtenerPorNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));
    }

    @Override
    public void registrarRolesPorDefecto() {
        List<String> roles = List.of("ADMINISTRADOR", "TECNICO", "CLIENTE");

        for (String nombre : roles) {
            rolRepository.findByNombreRol(nombre)
                .or(() -> {
                    Rol nuevo = new Rol();
                    nuevo.setNombreRol(nombre);
                    rolRepository.save(nuevo);
                    return java.util.Optional.of(nuevo);
                });
        }
    }
}

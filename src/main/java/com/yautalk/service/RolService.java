package com.yautalk.service;

import com.yautalk.model.Rol;

public interface RolService {
    Rol obtenerPorNombre(String nombreRol);
    void registrarRolesPorDefecto();
}

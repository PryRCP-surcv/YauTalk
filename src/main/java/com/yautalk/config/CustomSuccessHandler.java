package com.yautalk.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String rol = authority.getAuthority();

            switch (rol) {
                case "ROLE_ADMINISTRADOR" -> {
                    response.sendRedirect("/admin/inicio");
                    return;
                }
                case "ROLE_TECNICO" -> {
                    response.sendRedirect("/tecnico/inicio");
                    return;
                }
                case "ROLE_CLIENTE" -> {
                    response.sendRedirect("/cliente/inicio");
                    return;
                }
                default -> {
                }
            }
        }

        // Si no se encuentra rol válido
        response.sendRedirect("/login?error=rol");
    }
}

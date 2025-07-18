package com.yautalk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Para cargar archivos subidos dinámicamente (funciona como ya tienes)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // ✅ Para servir imágenes desde src/main/resources/static/img
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
    }
}

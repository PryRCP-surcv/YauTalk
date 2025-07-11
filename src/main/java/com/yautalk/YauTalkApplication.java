package com.yautalk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yautalk.service.RolService;

@SpringBootApplication
public class YauTalkApplication implements CommandLineRunner {

    private final RolService rolService;

    public YauTalkApplication(RolService rolService) {
        this.rolService = rolService;
    }

    public static void main(String[] args) {
        SpringApplication.run(YauTalkApplication.class, args);
    }

    @Override
    public void run(String... args) {
        rolService.registrarRolesPorDefecto();
    }
}

package com.carona.AppDeCarona.config;

import com.carona.AppDeCarona.entity.Admin;
import com.carona.AppDeCarona.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AdminConfig(AdminRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args){
        var admin = repository.findByEmail("admin@gmail.com");
        if (admin == null) {
            var novoAdmin = new Admin("admin@gmail.com", passwordEncoder.encode("123"));
            repository.save(novoAdmin);
            System.out.println("admin criado com sucesso!");
        } else {
            System.out.println("admin ja existente!");
        }
    }

}

package com.knf.dev.librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.knf.dev.librarymanagementsystem.entity.Role;
import com.knf.dev.librarymanagementsystem.entity.User;
import com.knf.dev.librarymanagementsystem.repository.UserRepository;

@SpringBootApplication
public class Application {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {

            // Create Role
            List<Role> roles = new ArrayList<>();
            roles.add(new Role("ROLE_ADMIN"));

            // Create Admin User
            User admin = new User(
                    "Admin",
                    "Admin",
                    "admin@admin.com",
                    passwordEncoder.encode("admin123"),
                    roles
            );

            userRepository.save(admin);
        };
    }

}

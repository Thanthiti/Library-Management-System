package com.example.thanthiti.Library.Management.System;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASS");
        String jwtSecret = dotenv.get("JWT_SECRET_BASE64", "defaultDevSecret");
        String adminEmail = dotenv.get("ADMIN_EMAIL");
        String adminPassword = dotenv.get("ADMIN_PASSWORD");
        String adminName = dotenv.get("ADMIN_NAME");

        if(dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalArgumentException("Missing DB environment variables!");
        }

        if(adminEmail == null || adminPassword == null || adminName == null) {
            throw new IllegalArgumentException("Missing ADMIN environment variables!");
        }

        // Set System properties
        System.setProperty("DB_URL", dbUrl);
        System.setProperty("DB_USER", dbUser);
        System.setProperty("DB_PASS", dbPassword);
        System.setProperty("JWT_SECRET_BASE64", jwtSecret);
        System.setProperty("ADMIN_EMAIL", adminEmail);
        System.setProperty("ADMIN_PASSWORD", adminPassword);
        System.setProperty("ADMIN_NAME", adminName);

        // Run Spring Boot
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }
}

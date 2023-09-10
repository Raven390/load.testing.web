package ru.develonica.load.testing.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
public class LoadTestingWebApp {
    public static void main(String[] args) {
        SpringApplication.run(LoadTestingWebApp.class, args);
    }
}

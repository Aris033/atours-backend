package com.atours.atours_backend.aplicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.atours.atours_backend")
@EntityScan(basePackages = "com.atours.atours_backend.*.domain")
@EnableJpaRepositories(basePackages = "com.atours.atours_backend.*.domain.repository")
public class AtoursBackendApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AtoursBackendApplication.class, args);
    }

}

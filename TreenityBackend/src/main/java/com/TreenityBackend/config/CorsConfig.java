package com.TreenityBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Consente il CORS su tutte le API
                .allowedOrigins("http://localhost:3000")  // Sostituisci con l'URL del tuo frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Permetti metodi HTTP necessari
                .allowedHeaders("*")  // Permetti tutte le intestazioni
                .allowCredentials(true);  // Consente i cookie (se necessario)
    }
}

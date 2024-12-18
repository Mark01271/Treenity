package com.TreenityBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //per ora autorizza tutti e ignora i token CSRF per ogni richiesta
    //DA MODIFICARE UNA VOLTA CREATO BACKOFFICE
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Consente l'accesso a tutte le richieste
            )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/**") // Ignora CSRF per tutte le richieste, da gestire una volta creato backoffice
                );
        return http.build();
    }
	
}

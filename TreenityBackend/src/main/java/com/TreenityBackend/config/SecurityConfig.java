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
    //TODO: gestire autenticazione admin e gestione CSFR una volta creato backoffice
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Consente l'accesso a tutte le richieste
            )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/**") // Ignora CSRF per ora, da gestire in seguito
                )
            //disabilitata per ora per test, da gestire, aggiunta commentata una possibile soluzione iniziale            
            //TODO: gestire la sicurezza degli headers HTTP
            .headers(headers -> headers.disable());
/*            .headers(headers -> headers
                    .addHeaderWriter((request, response) -> {
                        response.addHeader("Content-Security-Policy", "frame-ancestors 'self';"); // Consente iframe solo sullo stesso dominio
                    })
                );  */
        return http.build();
    }
	
}

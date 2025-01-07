package com.TreenityBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilizza BCrypt per codificare le password
    }
	
    /*scommentare per test
    //per ora autorizza tutti e ignora i token CSRF per ogni richiesta
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Consente l'accesso a tutte le richieste
            )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/**") // Ignora CSRF per ora, da gestire in seguito
                )
            //disabilitata per ora per test, da gestire, aggiunta commentata una possibile soluzione iniziale   
            .headers(headers -> headers.disable());
                );  
        return http.build();
       
    }
	*/
    
    //commentare per test se scommentato sopra
    
    //versione incompleta, TODO: Modificare i path con quelli corretti una volta creati
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           // Abilita CSRF 
           // .csrf(csrf -> csrf
           //     .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Usa un token CSRF conservato nei cookie
           //     .ignoringRequestMatchers("/public/**") // Percorsi esclusi dal controllo CSRF
           //     .requireCsrfProtectionMatcher(request -> 
           //         request.getRequestURI().matches("/public/form1|/public/form2")) //reimposta il controllo csrf nel caso dei form TODO: path temporanei da aggiornare una volta ricevuti i path veri
           //)
           // Configura gli accessi alle risorse
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/**") // Ignora CSRF per ora, da gestire in seguito
            )
            .authorizeHttpRequests(auth -> auth
            	//.requestMatchers("/admin/**", "/appointment-requests/**", "/info-requests/**", "/request-log/**", "/status/**" ).authenticated() // Protegge il backoffice richiedendo autenticazione
            	.anyRequest().permitAll() // Tutto il resto è accessibile senza login
            )
            // Configura il login
            .formLogin(form -> form
                .defaultSuccessUrl("/admin", true) // URL di destinazione dopo login riuscito
                .permitAll() // Permetti l'accesso alla pagina di login senza autenticazione
            )
            // Configura il logout
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Reindirizza alla homepage dopo il logout
                .invalidateHttpSession(true) // Invalida la sessione
                .deleteCookies("JSESSIONID", "XSRF-TOKEN") // Elimina i cookie che identificano la sessione e il token CSRF
                .permitAll()
            )
            // Configura gli header di sicurezza
            .headers(headers -> headers
            		//header che specifica la content securiy policy, nello specifico: peremete esecuzioni di script solo dal dominio princilpale,
            		//inpedisce il caricameno di objec, permete stili dal dominio principale , impedisce l`uso di iframe come protezione dal clickjacking
                    .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", 
                        "script-src 'self'; object-src 'none'; style-src 'self'; frame-ancestors 'none';"))
                    //previene che informazioni dalla pagina precedene vengano incluse nelle richiese https successive 
                    .addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "no-referrer"))
                    //impedisce al sio l`accesso a geolocalizzazione, microfono e webcam dell`uente
                    .addHeaderWriter(new StaticHeadersWriter("Permissions-Policy", "geolocation=(), microphone=(), camera=()"))
                    //impedisce di inserire il sio come iframe in alrti domini
                    .frameOptions(frame -> frame.deny()
                    //aggiunge l`header htst sul dominio e tutti i sub domini, imponendo al browser di usare solo connessioni HTTPS per un tempo massimo di 1 anno 
                    //TODO: informarsi bene su https://hstspreload.org ed in caso eseguire i passaggi
                    // momentaneamente disabilitata in quanto non in possesso di un cerificato SSL
                    /*.httpStrictTransportSecurity(hsts -> hsts
                         .includeSubDomains(true)
                         .maxAgeInSeconds(31536000))
                    */                                    		)
                );


        return http.build();
    }
}

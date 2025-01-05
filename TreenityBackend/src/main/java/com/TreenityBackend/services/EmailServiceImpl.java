package com.TreenityBackend.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendUserConfirmationEmail(String userEmail) {
        // Crea il messaggio per l'utente
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Conferma di ricezione richiesta");
        message.setText("La tua richiesta è stata presa in carico. Ti contatteremo al più presto per fornirti ulteriori dettagli.");

        // Invio dell'email
        javaMailSender.send(message);
    }

    @Override
    public void sendAdminNotificationEmail(String adminEmail, String requestDetails) {
        // Crea il messaggio per l'amministratore
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("Nuova richiesta ricevuta");
        message.setText("Hai ricevuto una nuova richiesta:\n\n" + requestDetails);

        // Invio dell'email
        javaMailSender.send(message);
    }
}

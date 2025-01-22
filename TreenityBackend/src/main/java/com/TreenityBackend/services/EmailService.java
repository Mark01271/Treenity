package com.TreenityBackend.services;

//Gestisce tutte le operazioni sulle email
public interface EmailService {
    //  Invia un'email di conferma all'utente
    void sendUserConfirmationEmail(String userEmail);
    // Invia una notifica via email all'amministratore
    void sendAdminNotificationEmail(String adminEmail, String requestDetails);
}

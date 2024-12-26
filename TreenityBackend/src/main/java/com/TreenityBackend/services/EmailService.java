package com.TreenityBackend.services;

public interface EmailService {
    void sendUserConfirmationEmail(String userEmail);
    void sendAdminNotificationEmail(String adminEmail, String requestDetails);
}

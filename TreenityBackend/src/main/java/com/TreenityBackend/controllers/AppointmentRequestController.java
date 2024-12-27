package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.services.AppointmentRequestService;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointment-requests")
@RequiredArgsConstructor
public class AppointmentRequestController {

    private AppointmentRequestService appointmentRequestService;
    private RequestLogService requestLogService;
    private EmailService emailService;

    // Endpoint per creare una nuova richiesta di appuntamento
    @PostMapping
    public AppointmentRequest createAppointmentRequest(@RequestBody AppointmentRequest request, @RequestParam Integer adminId) {
        // Crea un nuovo RequestLog con lo stato dinamico
        RequestLog requestLog = requestLogService.createRequestLog(request, adminId);

        // Salva la richiesta di appuntamento
        AppointmentRequest savedRequest = appointmentRequestService.saveAppointmentRequest(request);

        // Invia la conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail());

        // Invia notifica all'amministratore
        emailService.sendAdminNotificationEmail("admin@yourdomain.com", 
                "Nuova richiesta di appuntamento:\n\n" +
                "Gruppo: " + request.getGroupName() + "\n" +
                "Contatto: " + request.getContactPerson() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Telefono: " + request.getPhone());

        return savedRequest;
    }

    // Endpoint per ottenere tutte le richieste di appuntamento
    @GetMapping
    public List<AppointmentRequest> getAllAppointmentRequests() {
        return appointmentRequestService.getAllAppointmentRequests();
    }

    // Endpoint per ottenere una richiesta di appuntamento per ID
    @GetMapping("/{id}")
    public AppointmentRequest getAppointmentRequestById(@PathVariable Integer id) {
        return appointmentRequestService.getAppointmentRequestById(id).orElse(null);
    }
}

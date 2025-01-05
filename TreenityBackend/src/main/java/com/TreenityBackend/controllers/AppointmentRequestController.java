package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.exceptions.AppointmentRequestNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.services.AppointmentRequestService;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;
import com.TreenityBackend.services.StatusEntityService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointment-requests")
@RequiredArgsConstructor
@Validated
public class AppointmentRequestController {

    private final AppointmentRequestService appointmentRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;
    private final StatusEntityService statusEntityService;

    @PostMapping
    public AppointmentRequest createAppointmentRequest(@RequestBody AppointmentRequest request) {
        if (request == null) {
            throw new ValidationException("La richiesta non può essere nulla.");
        }

        // Recupera lo stato iniziale per il RequestLog
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status non trovato"));

        // Crea un nuovo RequestLog
        RequestLog requestLog = RequestLog.builder()
                .comment("Richiesta creata dal form")
                .status(status)
                .build();

        // Salva il RequestLog nel database
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Assegna il RequestLog alla richiesta di appuntamento
        request.setRequestLog(savedRequestLog);

        // Salva la richiesta di appuntamento
        AppointmentRequest savedRequest = appointmentRequestService.saveAppointmentRequest(request);

        // Invia la conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail());

        // Invia notifica all'amministratore
        String requestDetails = "Nuova richiesta di appuntamento:\n\n" +
                "Gruppo: " + request.getGroupName() + "\n" +
                "Contatto: " + request.getContactPerson() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Telefono: " + request.getPhone();
        emailService.sendAdminNotificationEmail("provatreenity@gmail.com", requestDetails);

        return savedRequest;
    }


    @GetMapping
    public List<AppointmentRequest> getAllAppointmentRequests() {
        List<AppointmentRequest> requests = appointmentRequestService.getAllAppointmentRequests();
        if (requests.isEmpty()) {
            throw new AppointmentRequestNotFoundException("Nessuna richiesta di appuntamento trovata.");
        }
        return requests;
    }

    @GetMapping("/{id}")
    public AppointmentRequest getAppointmentRequestById(@PathVariable Integer id) {
        return appointmentRequestService.getAppointmentRequestById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato"));
    }
}

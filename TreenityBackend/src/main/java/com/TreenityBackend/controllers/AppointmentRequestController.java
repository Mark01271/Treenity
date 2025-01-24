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

        // Crea un nuovo RequestLog preliminare
        RequestLog requestLog = RequestLog.builder()
                .comment("Richiesta creata dal form")
                .status(status)
                .build();

        // Salva il RequestLog preliminare
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Assegna il RequestLog preliminare alla richiesta
        request.setRequestLog(savedRequestLog);

        // Salva la richiesta di appuntamento
        AppointmentRequest savedRequest = appointmentRequestService.saveAppointmentRequest(request);

        // Aggiorna il RequestLog con l'ID della richiesta salvata
        savedRequestLog.setRelatedRequestId(savedRequest.getId());
        requestLogService.saveLog(savedRequestLog);

        // Invia la conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail(), request.getContactPerson());

        // Invia notifica all'amministratore con l'ID della richiesta
        String requestDetails = "Nuova richiesta di appuntamento:\n\n" +
                "ID richiesta: " + savedRequest.getId() + "\n" +
                "Gruppo: " + savedRequest.getGroupName() + "\n" +
                "Contatto: " + savedRequest.getContactPerson() + "\n" +
                "Email: " + savedRequest.getEmail() + "\n" +
                "Telefono: " + savedRequest.getPhone();
        emailService.sendAdminNotificationEmail("provatreenity@gmail.com", requestDetails);

        return savedRequest;
    }

    @GetMapping("/all")
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

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
import com.TreenityBackend.exceptions.AppointmentRequestNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.services.AppointmentRequestService;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointment-requests")
@RequiredArgsConstructor
public class AppointmentRequestController {

    private final AppointmentRequestService appointmentRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;

    @PostMapping
    public AppointmentRequest createAppointmentRequest(@RequestBody AppointmentRequest request, @RequestParam Integer adminId) {
        if (request == null || adminId == null) {
            throw new ValidationException("La richiesta o l'ID dell'amministratore non possono essere nulli.");
        }

        // Crea un nuovo RequestLog con lo stato dinamico
        RequestLog requestLog = requestLogService.createRequestLog(request, adminId);

        // Assegna il RequestLog alla richiesta di appuntamento
        request.setRequestLog(requestLog);

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
        emailService.sendAdminNotificationEmail("admin@yourdomain.com", requestDetails);

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
        if (id == null) {
            throw new ValidationException("L'ID della richiesta non può essere nullo.");
        }
        return appointmentRequestService.getAppointmentRequestById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato"));
    }
}

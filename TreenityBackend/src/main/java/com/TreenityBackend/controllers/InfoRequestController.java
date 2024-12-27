package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.InfoRequestService;
import com.TreenityBackend.services.RequestLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/info-requests")
@RequiredArgsConstructor
public class InfoRequestController {

    private InfoRequestService infoRequestService;
    private RequestLogService requestLogService;
    private EmailService emailService;

    // Endpoint per creare una nuova richiesta di informazioni
    @PostMapping
    public InfoRequest createInfoRequest(@RequestBody InfoRequest request, @RequestParam Integer adminId) {
        // Crea un nuovo RequestLog con lo stato dinamico
        RequestLog requestLog = requestLogService.createRequestLog(request, adminId);

        // Salva la richiesta di informazioni
        InfoRequest savedRequest = infoRequestService.saveInfoRequest(request);

        // Invia la conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail());

        // Invia notifica all'amministratore
        emailService.sendAdminNotificationEmail("admin@yourdomain.com",
                "Nuova richiesta di informazioni:\n\n" +
                "Gruppo: " + request.getGroupName() + "\n" +
                "Contatto: " + request.getContactPerson() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Telefono: " + request.getPhone());

        return savedRequest;
    }

    // Endpoint per ottenere tutte le richieste di informazioni
    @GetMapping
    public List<InfoRequest> getAllInfoRequests() {
        return infoRequestService.getAllInfoRequests();
    }

    // Endpoint per ottenere una richiesta di informazioni per ID
    @GetMapping("/{id}")
    public InfoRequest getInfoRequestById(@PathVariable Integer id) {
        return infoRequestService.getInfoRequestById(id).orElse(null);
    }
}
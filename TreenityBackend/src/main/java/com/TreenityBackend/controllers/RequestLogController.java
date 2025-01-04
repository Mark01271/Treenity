package com.TreenityBackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;
import com.TreenityBackend.services.StatusEntityService;
import com.TreenityBackend.entities.StatusEntity;

@RestController
@RequestMapping("/request-log")
public class RequestLogController {

    @Autowired
    private RequestLogService requestLogService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StatusEntityService statusEntityService;

    // Endpoint per ottenere tutti i RequestLog
    @GetMapping
    public List<RequestLog> getAllRequestLogs() {
        return requestLogService.getAllLogs();
    }

    // Endpoint per ottenere un RequestLog per ID
    @GetMapping("/{id}")
    public Optional<RequestLog> getRequestLogById(@PathVariable Integer id) {
        return requestLogService.getLogById(id);
    }

    // Endpoint per creare un nuovo RequestLog
    @PostMapping
    public RequestLog saveRequestLog(@RequestBody RequestLog requestLog) {
        // Aggiungere la logica per determinare lo status (opzionale)
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.RECEIVED)
            .orElseThrow(() -> new RuntimeException("Status non trovato"));

        // Impostiamo lo stato iniziale e la logica per prendere l'ID dell'admin (preso dal corpo della richiesta)
        requestLog.setStatus(status);

        // Salviamo il RequestLog
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Inviare le email se necessario
        String adminEmail = "admin@yourdomain.com"; // Sostituire con l'email effettiva dell'amministratore
        String requestDetails = "Nuovo RequestLog creato:\n\n" +
                                "Stato: " + status.getName() + "\n" +
                                "Commento: " + requestLog.getComment() + "\n";

        // Invio email all'amministratore
        emailService.sendAdminNotificationEmail(adminEmail, requestDetails);

        return savedRequestLog;
    }

    // Endpoint per aggiornare un RequestLog esistente
    @PutMapping("/{id}")
    public RequestLog updateRequestLog(@PathVariable Integer id, @RequestBody RequestLog requestLog) {
        // Aggiorniamo il RequestLog esistente
        requestLog.setId(id);
        RequestLog updatedRequestLog = requestLogService.updateRequestLog(id, requestLog);

        // Invio delle email in caso di aggiornamento
        String adminEmail = "admin@yourdomain.com"; // Sostituire con l'email effettiva dell'amministratore
        String requestDetails = "RequestLog aggiornato:\n\n" +
                                "ID: " + id + "\n" +
                                "Nuovo Stato: " + requestLog.getStatus().getName() + "\n" +
                                "Nuovo Commento: " + requestLog.getComment();

        // Invio email all'amministratore
        emailService.sendAdminNotificationEmail(adminEmail, requestDetails);

        return updatedRequestLog;
    }
}

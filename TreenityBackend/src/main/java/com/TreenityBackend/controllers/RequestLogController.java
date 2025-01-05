package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;
import com.TreenityBackend.services.StatusEntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request-log")
@RequiredArgsConstructor
public class RequestLogController {

    private final RequestLogService requestLogService;
    private final EmailService emailService;
    private final StatusEntityService statusEntityService;

    @GetMapping
    public List<RequestLog> getAllRequestLogs() {
        return requestLogService.getAllLogs();
    }

    @GetMapping("/{id}")
    public RequestLog getRequestLogById(@PathVariable Integer id) {
        return requestLogService.getLogById(id)
                .orElseThrow(() -> new RuntimeException("RequestLog con ID " + id + " non trovato."));
    }

    @PostMapping
    public RequestLog saveRequestLog(@RequestBody RequestLog requestLog) {
        // Trova lo stato iniziale
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
            .orElseThrow(() -> new RuntimeException("Status non trovato"));

        requestLog.setStatus(status);
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Invio email all'amministratore
        String adminEmail = "provatreenity@gmail.com";
        String requestDetails = "Nuovo RequestLog creato:\n\n" +
                                "Stato: " + status.getName() + "\n" +
                                "Commento: " + requestLog.getComment();
        emailService.sendAdminNotificationEmail(adminEmail, requestDetails);

        return savedRequestLog;
    }

    @PutMapping("/{id}")
    public RequestLog updateRequestLog(@PathVariable Integer id, @RequestBody RequestLog requestLog) {
        requestLog.setId(id);
        return requestLogService.updateRequestLog(id, requestLog);
    }
}

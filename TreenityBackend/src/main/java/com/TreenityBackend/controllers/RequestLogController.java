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
import com.TreenityBackend.services.RequestLogService;

@RestController
@RequestMapping("/request-log")
public class RequestLogController {

    @Autowired
    private RequestLogService requestLogService;

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

    // Endpoint per creare un nuovo RequestLog (di solito usato dal controller degli altri oggetti)
    @PostMapping
    public RequestLog saveRequestLog(@RequestBody RequestLog requestLog) {
        // Qui puoi aggiungere la logica per prendere l'ID dell'admin (ad esempio passato nel corpo della richiesta)
        Integer adminId = requestLog.getUpdatedBy().getId();  // L'ID dell'admin che ha effettuato l'operazione

        // Creiamo un nuovo log di richiesta
        return requestLogService.saveLog(requestLog);
    }

    // Endpoint per aggiornare un RequestLog esistente
    @PutMapping("/{id}")
    public RequestLog updateRequestLog(@PathVariable Integer id, @RequestBody RequestLog requestLog) {
        requestLog.setId(id);
        return requestLogService.saveLog(requestLog);
    }
}

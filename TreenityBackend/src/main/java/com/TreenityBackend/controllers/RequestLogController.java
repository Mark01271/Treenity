package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Controller per la gestione dei log delle richieste (RequestLog).
 * Permette di eseguire operazioni CRUD (Create, Read, Update) sui RequestLog.
 * Inoltre, invia notifiche via email agli amministratori per le modifiche ai log.
 */
@RestController
@RequestMapping("/request-log") 
@RequiredArgsConstructor  
public class RequestLogController {

    private final RequestLogService requestLogService;
    private final EmailService emailService1;
    private final StatusEntityService statusEntityService1;

    @Autowired
    private EmailService emailService; 
    @Autowired
    private StatusEntityService statusEntityService; 

    /**
     * Endpoint per ottenere tutti i RequestLog.
     * 
     * @return Una lista di tutti i log delle richieste
     */
    @GetMapping("/all")
    public List<RequestLog> getAllRequestLogs() {
        return requestLogService.getAllLogs();  // Ottiene e restituisce tutti i log delle richieste
    }

    /**
     * Endpoint per ottenere un RequestLog specifico tramite ID.
     * 
     * @param id L'ID del RequestLog da recuperare
     * @return Il RequestLog con l'ID specificato
     * @throws RuntimeException Se il log con l'ID specificato non viene trovato
     */
    @GetMapping("/{id}")
    public RequestLog getRequestLogById(@PathVariable Integer id) {
        return requestLogService.getLogById(id)  // Cerca il log tramite ID
                .orElseThrow(() -> new RuntimeException("RequestLog con ID " + id + " non trovato."));  // Se non trovato, lancia un'eccezione
    }

    /**
     * Endpoint per creare un nuovo RequestLog.
     * 
     * @param requestLog Oggetto RequestLog da salvare
     * @return Il RequestLog appena creato
     */
    @PostMapping
    public RequestLog saveRequestLog(@RequestBody RequestLog requestLog) {
        // Trova lo stato iniziale per il nuovo RequestLog (status "received")
        StatusEntity status = statusEntityService1.getStatusByName(StatusEntity.StatusName.received)
            .orElseThrow(() -> new RuntimeException("Status non trovato"));  // Se lo status non viene trovato, lancia un'eccezione

        // Imposta lo stato al RequestLog
        requestLog.setStatus(status);

        // Salva il RequestLog nel sistema
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Crea il contenuto della notifica email da inviare all'amministratore
        String adminEmail = "provatreenity@gmail.com";  // Email dell'amministratore
        String requestDetails = "Nuovo RequestLog creato:\n\n" +
                                "Stato: " + status.getName() + "\n" +
                                "Commento: " + requestLog.getComment();  // Dettagli della richiesta log

        // Invia una notifica via email all'amministratore
        emailService1.sendAdminNotificationEmail(adminEmail, requestDetails);

        // Restituisce il RequestLog appena creato
        return savedRequestLog;
    }

    /**
     * Endpoint per aggiornare un RequestLog esistente tramite ID.
     * 
     * @param id L'ID del RequestLog da aggiornare
     * @param requestLog Oggetto RequestLog con i nuovi dati
     * @return Il RequestLog aggiornato
     */
    @PutMapping("/{id}")
    public RequestLog updateRequestLog(@PathVariable Integer id, @RequestBody RequestLog requestLog) {
        requestLog.setId(id);  // Imposta l'ID del RequestLog che deve essere aggiornato
        return requestLogService.updateRequestLog(id, requestLog);  // Chiama il servizio per aggiornare il log e restituisce il log aggiornato
    }
}

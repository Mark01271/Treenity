package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.exceptions.InfoRequestNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.services.InfoRequestService;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.RequestLogService;
import com.TreenityBackend.services.StatusEntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/info-requests")
@RequiredArgsConstructor
@Validated
public class InfoRequestController {

    private final InfoRequestService infoRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;
    private final StatusEntityService statusEntityService;

    @PostMapping
    public InfoRequest createInfoRequest(@RequestBody InfoRequest request) {
        // 1. Validazione della richiesta
        if (request == null) {
            throw new ValidationException("La richiesta non può essere nulla.");
        }

        // 2. Recupero dello stato iniziale per RequestLog (status "received")
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status 'received' non trovato"));

        // 3. Creazione del RequestLog
        RequestLog requestLog = RequestLog.builder()
                .comment("Nuova richiesta di informazioni creata tramite form")
                .status(status)
                .build();

        // 4. Salvataggio del RequestLog
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // 5. Associazione del RequestLog alla InfoRequest
        request.setRequestLog(savedRequestLog);

        // 6. Salvataggio della InfoRequest
        InfoRequest savedRequest = infoRequestService.saveInfoRequest(request);

        // 7. Aggiungi il relatedRequestId al RequestLog appena creato
        savedRequestLog.setRelatedRequestId(savedRequest.getId());  // Collega l'ID della InfoRequest al RequestLog
        requestLogService.saveLog(savedRequestLog);  // Ritorna il log aggiornato

        // 8. Invio della conferma all'utente via email
        emailService.sendUserConfirmationEmail(request.getEmail(), request.getContactPerson());

        // 9. Invio della notifica all'amministratore con l'ID della richiesta
        String requestDetails = "Nuova richiesta di informazioni:\n\n" +
                "ID richiesta: " + savedRequest.getId() + "\n" +  // Aggiungi l'ID della richiesta
                "Gruppo: " + request.getGroupName() + "\n" +
                "Contatto: " + request.getContactPerson() + "\n" +
                "Email: " + request.getEmail() + "\n" +
                "Telefono: " + request.getPhone();
        emailService.sendAdminNotificationEmail("provatreenity@gmail.com", requestDetails);

        // Restituisci la richiesta salvata
        return savedRequest;
    }

    @GetMapping("/all")
    public List<InfoRequest> getAllInfoRequests() {
        List<InfoRequest> requests = infoRequestService.getAllInfoRequests();
        if (requests.isEmpty()) {
            throw new InfoRequestNotFoundException("Nessuna richiesta di informazioni trovata.");
        }
        return requests;
    }

    @GetMapping("/{id}")
    public InfoRequest getInfoRequestById(@PathVariable Integer id) {
        return infoRequestService.getInfoRequestById(id)
                .orElseThrow(() -> new InfoRequestNotFoundException("InfoRequest con ID " + id + " non trovato"));
    }
}

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

/**
 * Controller per la gestione delle richieste di informazioni.
 * Fornisce operazioni per la creazione, il recupero e la gestione delle richieste di informazioni.
 */
@RestController
@RequestMapping("/info-requests")
@RequiredArgsConstructor 
@Validated
public class InfoRequestController {

    // Iniezione delle dipendenze necessarie per i servizi
    private final InfoRequestService infoRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;
    private final StatusEntityService statusEntityService;

    /**
     * Crea una nuova richiesta di informazioni.
     * La funzione salva la richiesta, crea un log associato e invia notifiche via email.
     * 
     * @param request La richiesta di informazioni da creare
     * @return La richiesta di informazioni salvata
     * @throws ValidationException Se la richiesta è nulla
     */
    @PostMapping
    public InfoRequest createInfoRequest(@RequestBody InfoRequest request) {
        // 1. Verifica che la richiesta non sia nulla
        if (request == null) {
            throw new ValidationException("La richiesta non può essere nulla.");
        }

        // 2. Recupero dello stato iniziale per il RequestLog (stato "received")
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status 'received' non trovato"));

        // 3. Creazione di un nuovo RequestLog con lo stato "received"
        RequestLog requestLog = RequestLog.builder()
                .comment("Nuova richiesta di informazioni creata tramite form")
                .status(status)
                .build();

        // 4. Salvataggio del RequestLog creato
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // 5. Associazione del RequestLog alla richiesta di informazioni
        request.setRequestLog(savedRequestLog);

        // 6. Salvataggio della InfoRequest nel database
        InfoRequest savedRequest = infoRequestService.saveInfoRequest(request);

        // 7. Aggiornamento del RequestLog con l'ID della richiesta appena salvata
        savedRequestLog.setRelatedRequestId(savedRequest.getId());  // Collega l'ID della richiesta al RequestLog
        requestLogService.saveLog(savedRequestLog);  // Salva il log aggiornato

        // 8. Invio di una email di conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail(), request.getContactPerson());

        // 9. Creazione del messaggio di notifica da inviare all'amministratore
        String requestDetails = "Nuova richiesta di informazioni:\n\n" +
                "ID richiesta: " + savedRequest.getId() + "\n" +
                "Gruppo: " + savedRequest.getGroupName() + "\n" +
                "Tipo di gruppo: " + savedRequest.getGroupType() + "\n" +
                "Contatto: " + savedRequest.getContactPerson() + "\n" +
                "Email: " + savedRequest.getEmail() + "\n" +
                "Telefono: " + savedRequest.getPhone() + "\n" +
                "Intento dell'evento: " + savedRequest.getEventIntent() + "\n" +
                "Messaggio: " + savedRequest.getMessage() + "\n" +
                "Richieste aggiuntive: " + (savedRequest.getAdditionalRequests() != null ? savedRequest.getAdditionalRequests() : "Nessuna") + "\n" +
                "Data di creazione: " + savedRequest.getCreatedAt() + "\n";
        // 10. Invio della notifica via email all'amministratore
        emailService.sendAdminNotificationEmail("provatreenity@gmail.com", requestDetails);

        // 11. Restituisce la richiesta di informazioni appena salvata
        return savedRequest;
    }

    /**
     * Recupera tutte le richieste di informazioni.
     * 
     * @return La lista di tutte le richieste di informazioni
     * @throws InfoRequestNotFoundException Se non ci sono richieste di informazioni nel sistema
     */
    @GetMapping("/all")
    public List<InfoRequest> getAllInfoRequests() {
        // Recupera tutte le richieste di informazioni dal servizio
        List<InfoRequest> requests = infoRequestService.getAllInfoRequests();

        // Se non ci sono richieste, lancia un'eccezione
        if (requests.isEmpty()) {
            throw new InfoRequestNotFoundException("Nessuna richiesta di informazioni trovata.");
        }

        // Restituisce la lista delle richieste
        return requests;
    }

    /**
     * Recupera una richiesta di informazioni tramite ID.
     * 
     * @param id L'ID della richiesta di informazioni da recuperare
     * @return La richiesta di informazioni trovata
     * @throws InfoRequestNotFoundException Se la richiesta con l'ID specificato non viene trovata
     */
    @GetMapping("/{id}")
    public InfoRequest getInfoRequestById(@PathVariable Integer id) {
        // Recupera la richiesta tramite l'ID
        return infoRequestService.getInfoRequestById(id)
                .orElseThrow(() -> new InfoRequestNotFoundException("InfoRequest con ID " + id + " non trovato"));
    }
}
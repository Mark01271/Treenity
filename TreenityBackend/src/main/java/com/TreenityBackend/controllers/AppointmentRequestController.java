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

/**
 * Controller per la gestione delle richieste di appuntamento.
 * Esporta operazioni per creare, recuperare e gestire le richieste di appuntamento.
 */
@RestController
@RequestMapping("/appointment-requests")
@RequiredArgsConstructor 
@Validated 
public class AppointmentRequestController {

    
    private final AppointmentRequestService appointmentRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;
    private final StatusEntityService statusEntityService;

    /**
     * Crea una nuova richiesta di appuntamento.
     * Inizializza una richiesta, crea un log associato, invia notifiche via email
     * e salva la richiesta nel database.
     * 
     * @param request La richiesta di appuntamento da creare
     * @return La richiesta di appuntamento salvata
     * @throws ValidationException Se la richiesta è nulla
     */
    @PostMapping
    public AppointmentRequest createAppointmentRequest(@RequestBody AppointmentRequest request) {
        // Verifica che la richiesta non sia nulla
        if (request == null) {
            throw new ValidationException("La richiesta non può essere nulla.");
        }

        // Recupera lo stato iniziale per il RequestLog (stato "received")
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status non trovato"));

        // Crea un nuovo RequestLog con stato "received"
        RequestLog requestLog = RequestLog.builder()
                .comment("Richiesta creata dal form")
                .status(status)
                .build();

        // Salva il RequestLog preliminare
        RequestLog savedRequestLog = requestLogService.saveLog(requestLog);

        // Assegna il RequestLog preliminare alla richiesta di appuntamento
        request.setRequestLog(savedRequestLog);

        // Salva la richiesta di appuntamento
        AppointmentRequest savedRequest = appointmentRequestService.saveAppointmentRequest(request);

        // Aggiorna il RequestLog con l'ID della richiesta salvata
        savedRequestLog.setRelatedRequestId(savedRequest.getId());
        requestLogService.saveLog(savedRequestLog);

        // Invia una email di conferma all'utente
        emailService.sendUserConfirmationEmail(request.getEmail(), request.getContactPerson());

        // Prepara e invia la notifica all'amministratore
        String requestDetails = "Nuova richiesta di appuntamento ricevuta:\n\n" +
                "ID richiesta: " + savedRequest.getId() + "\n" +
                "Gruppo: " + savedRequest.getGroupName() + "\n" +
                "Tipo di gruppo: " + savedRequest.getGroupType() + "\n" +
                "Contatto: " + savedRequest.getContactPerson() + "\n" +
                "Email: " + savedRequest.getEmail() + "\n" +
                "Telefono: " + savedRequest.getPhone() + "\n" +
                "Data disponibilità: " + savedRequest.getAvailabilityDate() + "\n" +
                "Orario disponibilità: " + savedRequest.getAvailabilityTime() + "\n" +
                "Intento dell'evento: " + savedRequest.getEventIntent() + "\n" +
                "Messaggio: " + savedRequest.getMessage() + "\n" +
                "Richieste aggiuntive: " + (savedRequest.getAdditionalRequests() != null ? savedRequest.getAdditionalRequests() : "Nessuna") + "\n" +
                "Data di creazione: " + savedRequest.getCreatedAt() + "\n";
        emailService.sendAdminNotificationEmail("provatreenity@gmail.com", requestDetails);

        // Restituisce la richiesta di appuntamento salvata
        return savedRequest;
    }

    /**
     * Recupera tutte le richieste di appuntamento.
     * 
     * @return La lista di tutte le richieste di appuntamento
     * @throws AppointmentRequestNotFoundException Se non sono state trovate richieste di appuntamento
     */
    @GetMapping("/all")
    public List<AppointmentRequest> getAllAppointmentRequests() {
        // Recupera tutte le richieste di appuntamento dal servizio
        List<AppointmentRequest> requests = appointmentRequestService.getAllAppointmentRequests();

        // Se non ci sono richieste, lancia un'eccezione
        if (requests.isEmpty()) {
            throw new AppointmentRequestNotFoundException("Nessuna richiesta di appuntamento trovata.");
        }

        // Restituisce la lista delle richieste
        return requests;
    }

    /**
     * Recupera una richiesta di appuntamento tramite ID.
     * 
     * @param id L'ID della richiesta di appuntamento da recuperare
     * @return La richiesta di appuntamento trovata
     * @throws AppointmentRequestNotFoundException Se la richiesta con l'ID specificato non viene trovata
     */
    @GetMapping("/{id}")
    public AppointmentRequest getAppointmentRequestById(@PathVariable Integer id) {
        // Recupera la richiesta tramite il servizio usando l'ID
        return appointmentRequestService.getAppointmentRequestById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato"));
    }
}



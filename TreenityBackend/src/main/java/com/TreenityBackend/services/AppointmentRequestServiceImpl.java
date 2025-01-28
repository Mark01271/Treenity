package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.exceptions.AppointmentRequestNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.repos.AppointmentRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

/**
 * Implementazione del servizio per la gestione delle richieste di appuntamento (AppointmentRequest).
 * Il servizio include operazioni come il recupero, la creazione e l'aggiornamento dello stato delle richieste di appuntamento.
 */
@Service
@RequiredArgsConstructor
public class AppointmentRequestServiceImpl implements AppointmentRequestService {

    private final AppointmentRequestDAO appointmentRequestDAO;
    private final RequestLogDAO requestLogDAO;

    /**
     * Recupera tutte le richieste di appuntamento.
     *
     * @return Una lista di tutte le richieste di appuntamento
     */
    @Override
    public List<AppointmentRequest> getAllAppointmentRequests() {
        return appointmentRequestDAO.findAll();
    }

    /**
     * Recupera una richiesta di appuntamento specifica tramite il suo ID.
     *
     * @param id L'ID della richiesta di appuntamento
     * @return Un oggetto Optional contenente la richiesta di appuntamento trovata
     * @throws AppointmentRequestNotFoundException Se la richiesta di appuntamento non è stata trovata
     */
    @Override
    public Optional<AppointmentRequest> getAppointmentRequestById(Integer id) {
        return Optional.of(appointmentRequestDAO.findById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato")));
    }

    /**
     * Recupera tutte le richieste di appuntamento associate a un determinato ID di RequestLog.
     *
     * @param logId L'ID del RequestLog
     * @return Una lista di richieste di appuntamento associate all'ID del RequestLog
     * @throws ValidationException Se l'ID del RequestLog è nullo
     * @throws AppointmentRequestNotFoundException Se non ci sono richieste di appuntamento associate a quel log
     */
    @Override
    public List<AppointmentRequest> getAppointmentRequestsByRequestLogId(Integer logId) {
        // Controllo che l'ID del log non sia nullo
        if (logId == null) {
            throw new ValidationException("L'ID del RequestLog non può essere nullo.");
        }
        // Recupera le richieste di appuntamento in base all'ID del RequestLog
        List<AppointmentRequest> requests = appointmentRequestDAO.findByRequestLog_Id(logId);
        if (requests.isEmpty()) {
            throw new AppointmentRequestNotFoundException("Nessuna richiesta trovata per RequestLog con ID " + logId);
        }
        return requests;
    }

    /**
     * Salva una nuova richiesta di appuntamento.
     * La richiesta di appuntamento deve essere valida (non nulla).
     *
     * @param appointmentRequest La richiesta di appuntamento da salvare
     * @return La richiesta di appuntamento salvata
     * @throws ValidationException Se la richiesta di appuntamento è nulla
     */
    @Override
    public AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest) {
        // Controllo che la richiesta di appuntamento non sia nulla
        if (appointmentRequest == null) {
            throw new ValidationException("La richiesta di appuntamento non può essere nulla.");
        }
        // Salva e restituisce la richiesta di appuntamento
        return appointmentRequestDAO.save(appointmentRequest);
    }

    /**
     * Aggiorna lo stato di una richiesta di appuntamento.
     * La richiesta di appuntamento e l'ID dello stato devono essere validi (non nulli).
     *
     * @param appointmentRequest La richiesta di appuntamento da aggiornare
     * @param statusId L'ID del nuovo stato da assegnare alla richiesta di appuntamento
     * @return La richiesta di appuntamento aggiornata
     * @throws ValidationException Se la richiesta di appuntamento o lo stato non sono validi
     */
    @Override
    public AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId) {
        // Controllo che la richiesta di appuntamento e lo stato non siano nulli
        if (appointmentRequest == null || statusId == null) {
            throw new ValidationException("AppointmentRequest e statusId non possono essere nulli.");
        }

        // Logica di aggiornamento dello stato (da implementare)
        // Qui si dovrebbe aggiungere la logica per aggiornare lo stato della richiesta
        return appointmentRequestDAO.save(appointmentRequest);
    }
}

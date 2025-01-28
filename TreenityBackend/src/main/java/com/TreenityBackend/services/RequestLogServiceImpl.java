package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

/**
 * Implementazione del servizio per la gestione dei RequestLog.
 * Questo servizio consente la creazione, il recupero, l'aggiornamento e la gestione dei log associati alle richieste.
 */
@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements RequestLogService {

    private final AdminService adminService;
    private final StatusEntityService statusEntityService;
    private final RequestLogDAO requestLogDao;

    /**
     * Crea un nuovo RequestLog associato a una richiesta (AppointmentRequest o InfoRequest).
     * 
     * @param request L'oggetto di richiesta (AppointmentRequest o InfoRequest)
     * @param adminId L'ID dell'amministratore che sta creando il log
     * @return Il RequestLog creato
     * @throws RuntimeException Se l'Admin o lo StatusEntity non sono trovati
     */
    @Override
    public RequestLog createRequestLog(Object request, Integer adminId) {
        // Recupera l'amministratore tramite il suo ID
        Admin admin = adminService.getAdminById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

        // Recupera lo stato "received"
        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        Integer relatedRequestId = null;

        // Ottieni l'ID della richiesta correlata (AppointmentRequest o InfoRequest)
        if (request instanceof AppointmentRequest) {
            relatedRequestId = ((AppointmentRequest) request).getId();  // Ottieni l'ID dell'AppointmentRequest
        } else if (request instanceof InfoRequest) {
            relatedRequestId = ((InfoRequest) request).getId();  // Ottieni l'ID dell'InfoRequest
        }

        // Crea il RequestLog
        RequestLog requestLog = RequestLog.builder()
                .updatedBy(admin)        // L'amministratore che aggiorna il log
                .status(status)          // Lo stato della richiesta
                .comment("Nuova richiesta generata") // Commento del log
                .relatedRequestId(relatedRequestId)  // ID della richiesta correlata (AppointmentRequest o InfoRequest)
                .build();

        // Salva il RequestLog nel database
        return requestLogDao.save(requestLog);
    }

    /**
     * Recupera tutti i RequestLog presenti nel sistema.
     * 
     * @return Una lista di tutti i RequestLog
     */
    @Override
    public List<RequestLog> getAllLogs() {
        // Restituisce tutti i RequestLog
        return requestLogDao.findAll();
    }

    /**
     * Recupera un RequestLog specifico tramite il suo ID.
     * 
     * @param id L'ID del RequestLog da recuperare
     * @return Un oggetto Optional contenente il RequestLog, se trovato
     */
    @Override
    public Optional<RequestLog> getLogById(Integer id) {
        // Recupera il RequestLog tramite l'ID
        return requestLogDao.findById(id);
    }

    /**
     * Salva un nuovo RequestLog.
     * 
     * @param requestLog Il RequestLog da salvare
     * @return Il RequestLog salvato
     */
    @Override
    public RequestLog saveLog(RequestLog requestLog) {
        // Salva il RequestLog nel database
        return requestLogDao.save(requestLog);
    }

    /**
     * Aggiorna un RequestLog esistente con nuove informazioni.
     * 
     * @param id L'ID del RequestLog da aggiornare
     * @param updatedRequestLog L'oggetto RequestLog con i nuovi dati
     * @return Il RequestLog aggiornato
     * @throws RuntimeException Se il RequestLog con l'ID specificato non è trovato
     */
    @Override
    public RequestLog updateRequestLog(Integer id, RequestLog updatedRequestLog) {
        // Recupera il RequestLog esistente tramite l'ID
        RequestLog existingLog = requestLogDao.findById(id).orElseThrow(() -> new RuntimeException("RequestLog not found"));

        // Aggiorna lo stato e il commento del RequestLog
        existingLog.setStatus(updatedRequestLog.getStatus());
        existingLog.setComment(updatedRequestLog.getComment());

        // Salva il RequestLog aggiornato nel database
        return requestLogDao.save(existingLog);
    }
}

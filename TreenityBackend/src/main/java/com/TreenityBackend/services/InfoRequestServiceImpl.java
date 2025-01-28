package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.exceptions.InfoRequestNotFoundException;
import com.TreenityBackend.repos.InfoRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

/**
 * Implementazione del servizio per la gestione delle richieste di informazioni.
 * La classe fornisce metodi per salvare, recuperare, e aggiornare le richieste di informazioni.
 */
@Service
@RequiredArgsConstructor
public class InfoRequestServiceImpl implements InfoRequestService {

    private final InfoRequestDAO infoRequestDAO;
    private final RequestLogDAO requestLogDAO;

    /**
     * Salva una nuova richiesta di informazioni nel sistema.
     *
     * @param infoRequest L'oggetto InfoRequest da salvare
     * @return L'oggetto InfoRequest salvato
     */
    @Override
    public InfoRequest saveInfoRequest(InfoRequest infoRequest) {
        // Salva la richiesta di informazioni nel database e restituisce l'entità salvata
        return infoRequestDAO.save(infoRequest);
    }

    /**
     * Recupera tutte le richieste di informazioni presenti nel sistema.
     *
     * @return Una lista di tutte le richieste di informazioni
     */
    @Override
    public List<InfoRequest> getAllInfoRequests() {
        // Restituisce tutte le informazioni delle richieste
        return infoRequestDAO.findAll();
    }

    /**
     * Recupera una richiesta di informazioni specifica tramite il suo ID.
     *
     * @param id L'ID della richiesta di informazioni da recuperare
     * @return Un oggetto Optional contenente la richiesta di informazioni, se presente
     * @throws InfoRequestNotFoundException Se la richiesta di informazioni con l'ID specificato non è trovata
     */
    @Override
    public Optional<InfoRequest> getInfoRequestById(Integer id) {
        // Recupera la richiesta di informazioni tramite l'ID
        return Optional.of(infoRequestDAO.findById(id)
            .orElseThrow(() -> new InfoRequestNotFoundException("InfoRequest con ID " + id + " non trovato")));
    }

    /**
     * Recupera tutte le richieste di informazioni associate a un determinato log ID.
     *
     * @param logId L'ID del log a cui associare le richieste
     * @return Una lista di InfoRequest correlate al log ID
     */
    @Override
    public List<InfoRequest> getInfoRequestsByRequestLogId(Integer logId) {
        // Restituisce tutte le richieste di informazioni associate al RequestLog
        return infoRequestDAO.findByRequestLog_Id(logId);
    }

    /**
     * Aggiorna lo stato di una richiesta di informazioni.
     *
     * @param infoRequest L'oggetto InfoRequest da aggiornare
     * @param statusId L'ID dello stato da aggiornare per la richiesta
     * @return L'oggetto InfoRequest aggiornato
     */
    @Override
    public InfoRequest updateInfoRequestStatus(InfoRequest infoRequest, Integer statusId) {
        // Salva l'InfoRequest con lo stato aggiornato
        return infoRequestDAO.save(infoRequest);
    }
}

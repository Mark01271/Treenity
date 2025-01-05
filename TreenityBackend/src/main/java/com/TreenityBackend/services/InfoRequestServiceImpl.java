package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.exceptions.InfoRequestNotFoundException;
import com.TreenityBackend.repos.InfoRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfoRequestServiceImpl implements InfoRequestService {

    private final InfoRequestDAO infoRequestDAO;
    private final RequestLogDAO requestLogDAO;

    @Override
    public InfoRequest saveInfoRequest(InfoRequest infoRequest) {
        return infoRequestDAO.save(infoRequest);
    }

    @Override
    public List<InfoRequest> getAllInfoRequests() {
        return infoRequestDAO.findAll();
    }

    @Override
    public Optional<InfoRequest> getInfoRequestById(Integer id) {
        return Optional.of(infoRequestDAO.findById(id)
            .orElseThrow(() -> new InfoRequestNotFoundException("InfoRequest con ID " + id + " non trovato")));
    }

    @Override
    public List<InfoRequest> getInfoRequestsByRequestLogId(Integer logId) {
        return infoRequestDAO.findByRequestLog_Id(logId);
    }

    @Override
    public InfoRequest updateInfoRequestStatus(InfoRequest infoRequest, Integer statusId) {
        // Esegui l'aggiornamento e restituisci
        return infoRequestDAO.save(infoRequest);
    }
}

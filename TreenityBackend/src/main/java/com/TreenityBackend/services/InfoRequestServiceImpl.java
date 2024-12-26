package com.TreenityBackend.services;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.repos.InfoRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;

@Service
@RequiredArgsConstructor
public class InfoRequestServiceImpl implements InfoRequestService {

    private InfoRequestDAO infoRequestDAO;
    private RequestLogDAO requestLogDAO;

    @Override
    // Salva informazioni della request
    public InfoRequest saveInfoRequest(InfoRequest infoRequest) {
        return infoRequestDAO.save(infoRequest);
    }
	
    @Override
    // Ottieni tutte info della request
    public List<InfoRequest> getAllInfoRequests() {
        return infoRequestDAO.findAll();
    }
	
    @Override
	// Ottieni info della request da id
    public Optional<InfoRequest> getInfoRequestById(Integer id) {
        return infoRequestDAO.findById(id);
    }
    
    @Override
    // Ottieni info della request da id del log
    public List<InfoRequest> getInfoRequestsByRequestLogId(Integer logId) {
        return infoRequestDAO.findByRequestLog_Id(logId);
    }
    
    @Override
    // Aggiorna stato della request
    public InfoRequest updateInfoRequestStatus(InfoRequest infoRequest, Integer statusId) {
        return infoRequestDAO.save(infoRequest);
    }
}


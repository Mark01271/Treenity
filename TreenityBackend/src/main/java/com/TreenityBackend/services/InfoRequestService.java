package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import com.TreenityBackend.entities.InfoRequest;

public interface InfoRequestService {
	
    // Salva informazioni della request
    InfoRequest saveInfoRequest(InfoRequest infoRequest);
	
    // Ottieni tutte info della request
	List<InfoRequest> getAllInfoRequests();
	
	// Ottieni info della request da id
    Optional<InfoRequest> getInfoRequestById(Integer id);
    
    // Ottieni info della request da id del log
    List<InfoRequest> getInfoRequestsByRequestLogId(Integer logId);
    
    // Aggiorna stato della request
    InfoRequest updateInfoRequestStatus(InfoRequest infoRequest, Integer statusId);
}

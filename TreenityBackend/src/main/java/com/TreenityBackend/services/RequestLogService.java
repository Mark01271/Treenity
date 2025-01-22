package com.TreenityBackend.services;

import com.TreenityBackend.entities.RequestLog;

import java.util.List;
import java.util.Optional;

public interface RequestLogService {
    // Crea un nuovo RequestLog
    RequestLog createRequestLog(Object request, Integer adminId);
    // Ottieni tutti i RequestLog
    List<RequestLog> getAllLogs();
    // Ottieni un RequestLog per ID
    Optional<RequestLog> getLogById(Integer id);
    // Salva un RequestLog
    RequestLog saveLog(RequestLog requestLog);  
    // Aggiorna un RequestLog
    RequestLog updateRequestLog(Integer id, RequestLog updatedRequestLog);
}

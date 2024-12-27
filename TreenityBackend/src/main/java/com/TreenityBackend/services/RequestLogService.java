package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import com.TreenityBackend.entities.RequestLog;

public interface RequestLogService {
    RequestLog createRequestLog(Object request, Integer adminId);
    List<RequestLog> getAllLogs();
    Optional<RequestLog> getLogById(Integer id);
    RequestLog saveLog(RequestLog requestLog);  // Metodo per salvare un log
    RequestLog updateRequestLog(Integer id, RequestLog updatedRequestLog);
}

package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestLogService {
    private final RequestLogDAO requestLogRepository;

    // Recupera tutti i log
    public List<RequestLog> getAllLogs() {
        return requestLogRepository.findAll();
    }

    // Trova un log per ID
    public Optional<RequestLog> getLogById(Integer id) {
        return requestLogRepository.findById(id);
    }

    // Trova i log aggiornati da un admin specifico
    public List<RequestLog> getLogsByAdminId(Integer adminId) {
        return requestLogRepository.findByUpdatedBy_Id(adminId);
    }

    // Trova i log con uno specifico stato
    public List<RequestLog> getLogsByStatusId(Integer statusId) {
        return requestLogRepository.findByStatus_Id(statusId);
    }

    // Salva un nuovo log
    public RequestLog saveLog(RequestLog requestLog) {
        return requestLogRepository.save(requestLog);
    }
}

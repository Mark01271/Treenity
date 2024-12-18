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
    private final RequestLogDAO requestLogDao;

    // Recupera tutti i log
    public List<RequestLog> getAllLogs() {
        return requestLogDao.findAll();
    }

    // Trova un log per ID
    public Optional<RequestLog> getLogById(Integer id) {
        return requestLogDao.findById(id);
    }

    // Trova i log aggiornati da un admin specifico
    public List<RequestLog> getLogsByAdminId(Integer adminId) {
        return requestLogDao.findByUpdatedBy_Id(adminId);
    }

    // Trova i log con uno specifico stato
    public List<RequestLog> getLogsByStatusId(Integer statusId) {
        return requestLogDao.findByStatus_Id(statusId);
    }

    // Salva un nuovo log
    public RequestLog saveLog(RequestLog requestLog) {
        return requestLogDao.save(requestLog);
    }
}

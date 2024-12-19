package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements RequestLogService {
    private RequestLogDAO requestLogDao;

    @Override
    public List<RequestLog> getAllLogs() {
        return requestLogDao.findAll();
    }

    @Override
    public Optional<RequestLog> getLogById(Integer id) {
        return requestLogDao.findById(id);
    }

    @Override
    public List<RequestLog> getLogsByAdminId(Integer adminId) {
        return requestLogDao.findByUpdatedBy_Id(adminId);
    }

    @Override
    public List<RequestLog> getLogsByStatusId(Integer statusId) {
        return requestLogDao.findByStatus_Id(statusId);
    }

    @Override
    public RequestLog saveLog(RequestLog requestLog) {
        return requestLogDao.save(requestLog);
    }
}

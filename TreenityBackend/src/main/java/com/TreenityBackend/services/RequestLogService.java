package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import com.TreenityBackend.entities.RequestLog;

public interface RequestLogService {
    List<RequestLog> getAllLogs();
    Optional<RequestLog> getLogById(Integer id);
    List<RequestLog> getLogsByAdminId(Integer adminId);
    List<RequestLog> getLogsByStatusId(Integer statusId);
    RequestLog saveLog(RequestLog requestLog);
}

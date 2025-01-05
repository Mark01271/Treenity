package com.TreenityBackend.services;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.repos.RequestLogDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements RequestLogService {

    private final AdminService adminService;
    private final StatusEntityService statusEntityService;
    private final RequestLogDAO requestLogDao;

    // Crea un nuovo RequestLog
    @Override
    public RequestLog createRequestLog(Object request, Integer adminId) {
        Admin admin = adminService.getAdminById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

        StatusEntity status = statusEntityService.getStatusByName(StatusEntity.StatusName.received)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        RequestLog requestLog = RequestLog.builder()
                .updatedBy(admin)
                .status(status)
                .comment("Nuova richiesta generata")
                .build();

        return requestLogDao.save(requestLog);
    }

    // Ottieni tutti i RequestLog
    @Override
    public List<RequestLog> getAllLogs() {
        return requestLogDao.findAll();
    }

    // Ottieni un RequestLog per ID
    @Override
    public Optional<RequestLog> getLogById(Integer id) {
        return requestLogDao.findById(id);
    }

    // Salva un RequestLog
    @Override
    public RequestLog saveLog(RequestLog requestLog) {
        return requestLogDao.save(requestLog);
    }

    // Aggiorna un RequestLog
    @Override
    public RequestLog updateRequestLog(Integer id, RequestLog updatedRequestLog) {
        RequestLog existingLog = requestLogDao.findById(id).orElseThrow(() -> new RuntimeException("RequestLog not found"));
        existingLog.setStatus(updatedRequestLog.getStatus());
        existingLog.setComment(updatedRequestLog.getComment());
        return requestLogDao.save(existingLog);
    }
}

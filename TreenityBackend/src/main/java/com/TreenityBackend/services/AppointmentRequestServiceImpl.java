package com.TreenityBackend.services;

import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.repos.AppointmentRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentRequestServiceImpl implements AppointmentRequestService {

    private AppointmentRequestDAO appointmentRequestDAO;
    private RequestLogDAO requestLogDAO;

    @Override
    public List<AppointmentRequest> getAllAppointmentRequests() {
        return appointmentRequestDAO.findAll();
    }

    @Override
    public Optional<AppointmentRequest> getAppointmentRequestById(Integer id) {
        return appointmentRequestDAO.findById(id);
    }

    @Override
    public List<AppointmentRequest> getAppointmentRequestsByRequestLogId(Integer logId) {
        return appointmentRequestDAO.findByRequestLog_Id(logId);
    }

    @Override
    public AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest) {
        return appointmentRequestDAO.save(appointmentRequest);
    }

    @Override
    public AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId) {
        // Update logic for changing the status via RequestLog
        // Implement this as needed
        return appointmentRequestDAO.save(appointmentRequest);
    }
}

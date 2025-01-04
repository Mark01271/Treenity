package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.AppointmentRequest;
import com.TreenityBackend.exceptions.AppointmentRequestNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.repos.AppointmentRequestDAO;
import com.TreenityBackend.repos.RequestLogDAO;

import lombok.RequiredArgsConstructor;

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
        return Optional.of(appointmentRequestDAO.findById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato")));
    }

    @Override
    public List<AppointmentRequest> getAppointmentRequestsByRequestLogId(Integer logId) {
        if (logId == null) {
            throw new ValidationException("L'ID del RequestLog non può essere nullo.");
        }
        List<AppointmentRequest> requests = appointmentRequestDAO.findByRequestLog_Id(logId);
        if (requests.isEmpty()) {
            throw new AppointmentRequestNotFoundException("Nessuna richiesta trovata per RequestLog con ID " + logId);
        }
        return requests;
    }

    @Override
    public AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest) {
        if (appointmentRequest == null) {
            throw new ValidationException("La richiesta di appuntamento non può essere nulla.");
        }
        return appointmentRequestDAO.save(appointmentRequest);
    }

    @Override
    public AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId) {
        if (appointmentRequest == null || statusId == null) {
            throw new ValidationException("AppointmentRequest e statusId non possono essere nulli.");
        }

        // Logica di aggiornamento dello stato da implementare
        return appointmentRequestDAO.save(appointmentRequest);
    }
}

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

    private final AppointmentRequestDAO appointmentRequestDAO;
    private final RequestLogDAO requestLogDAO;

    //Recupera tutte le richieste di appuntamento
    @Override
    public List<AppointmentRequest> getAllAppointmentRequests() {
        return appointmentRequestDAO.findAll();
    }

    // Recupera una richiesta di appuntamento specifica in base al suo ID
    @Override
    public Optional<AppointmentRequest> getAppointmentRequestById(Integer id) {
        return Optional.of(appointmentRequestDAO.findById(id)
                .orElseThrow(() -> new AppointmentRequestNotFoundException("AppointmentRequest con ID " + id + " non trovato")));
    }

    // Recupera tutte le richieste di appuntamento associate a un determinato log ID
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

    // Salva una nuova richiesta di appuntamento
    @Override
    public AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest) {
        if (appointmentRequest == null) {
            throw new ValidationException("La richiesta di appuntamento non può essere nulla.");
        }
        return appointmentRequestDAO.save(appointmentRequest);
    }

    // Aggiorna lo stato di una richiesta di appuntamento
    @Override
    public AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId) {
        if (appointmentRequest == null || statusId == null) {
            throw new ValidationException("AppointmentRequest e statusId non possono essere nulli.");
        }

        // Logica di aggiornamento dello stato da implementare
        return appointmentRequestDAO.save(appointmentRequest);
    }
}

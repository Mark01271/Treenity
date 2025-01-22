package com.TreenityBackend.services;

import com.TreenityBackend.entities.AppointmentRequest;

import java.util.List;
import java.util.Optional;

//Gestisce tutte le operazioni sulle richieste di appuntamenti
public interface AppointmentRequestService {
	// Recupera tutte le richieste di appuntamento
    List<AppointmentRequest> getAllAppointmentRequests();
    // Recupera una richiesta di appuntamento specifica in base al suo ID
    Optional<AppointmentRequest> getAppointmentRequestById(Integer id);
    // Recupera tutte le richieste di appuntamento associate a un determinato log ID
    List<AppointmentRequest> getAppointmentRequestsByRequestLogId(Integer logId);
    // Salva una nuova richiesta di appuntamento
    AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest);
    // Aggiorna lo stato di una richiesta di appuntamento
    AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId);
}

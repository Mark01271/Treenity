package com.TreenityBackend.services;

import com.TreenityBackend.entities.AppointmentRequest;

import java.util.List;
import java.util.Optional;

public interface AppointmentRequestService {
    List<AppointmentRequest> getAllAppointmentRequests();
    Optional<AppointmentRequest> getAppointmentRequestById(Integer id);
    List<AppointmentRequest> getAppointmentRequestsByRequestLogId(Integer logId);
    AppointmentRequest saveAppointmentRequest(AppointmentRequest appointmentRequest);
    AppointmentRequest updateAppointmentRequestStatus(AppointmentRequest appointmentRequest, Integer statusId);
}

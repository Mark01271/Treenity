package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.AppointmentRequest;

@Repository
public interface AppointmentRequestDAO extends JpaRepository<AppointmentRequest, Integer> {
    // Trova richieste di appuntamento con un determinato status
    List<AppointmentRequest> findByStatus_Id(Integer statusId);

    // Trova richieste di appuntamento con una data specifica
    List<AppointmentRequest> findByAvailabilityDateContaining(String availabilityDate);

    // Trova richieste con una preferenza specifica di orario
    List<AppointmentRequest> findByAvailabilityTime(AppointmentRequest.AvailabilityTime availabilityTime);

    // Ordina le richieste per data di creazione
    List<AppointmentRequest> findAllByOrderByCreatedAtDesc();
}

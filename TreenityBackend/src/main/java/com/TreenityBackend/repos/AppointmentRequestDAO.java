package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.AppointmentRequest;

@Repository
public interface AppointmentRequestDAO extends JpaRepository<AppointmentRequest, Integer> {
	//trova una l'appointment request contenente una request log dato l'id
    List<AppointmentRequest> findByRequestLog_Id(Integer requestLogId);
    //controlla se la data inserita è disponibile per una visita
    List<AppointmentRequest> findByAvailabilityDateContaining(String availabilityDate);
    //controlla se l'orario inserito è disponibile per una visita
    List<AppointmentRequest> findByAvailabilityTime(AppointmentRequest.AvailabilityTime availabilityTime);
    //trova tutti gli appuntamenti ordinati dalla data di creazione in ordine decrescente
    List<AppointmentRequest> findAllByOrderByCreatedAtDesc();
}
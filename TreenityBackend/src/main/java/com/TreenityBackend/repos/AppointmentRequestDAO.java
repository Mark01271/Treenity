package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.AppointmentRequest;

@Repository
public interface AppointmentRequestDAO extends JpaRepository<AppointmentRequest, Integer> {
    List<AppointmentRequest> findByRequestLog_Id(Integer requestLogId);
    List<AppointmentRequest> findByAvailabilityDateContaining(String availabilityDate);
    List<AppointmentRequest> findByAvailabilityTime(AppointmentRequest.AvailabilityTime availabilityTime);
    List<AppointmentRequest> findAllByOrderByCreatedAtDesc();
}
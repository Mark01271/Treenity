package com.TreenityBackend.repos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.RequestLog;

@Repository
public interface RequestLogDAO extends JpaRepository<RequestLog, Integer> {
    // Trova i log aggiornati da uno specifico Admin
    List<RequestLog> findByUpdatedBy_Id(Integer adminId);

    // Trova i log con uno specifico stato
    List<RequestLog> findByStatus_Id(Integer statusId);

    // Ordina i log per data di aggiornamento decrescente
    List<RequestLog> findAllByOrderByUpdatedAtDesc();
}



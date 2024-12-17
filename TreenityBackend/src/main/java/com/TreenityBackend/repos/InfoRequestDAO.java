package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.InfoRequest;

@Repository
public interface InfoRequestDAO extends JpaRepository<InfoRequest, Integer> {
    // Trova richieste di informazioni con un determinato status
    List<InfoRequest> findByStatus_Id(Integer statusId);

    // Trova richieste di informazioni con un determinato gruppo
    List<InfoRequest> findByGroupName(String groupName);

    // Trova richieste con newsletter attivata
    List<InfoRequest> findByNewsletterTrue();

    // Ordina richieste per data di creazione
    List<InfoRequest> findAllByOrderByCreatedAtDesc();
}

package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TreenityBackend.entities.RequestLog;

@Repository
public interface RequestLogDAO extends JpaRepository<RequestLog, Integer> {

    // Trova tutti i RequestLog con uno stato specifico
    List<RequestLog> findByStatus(RequestLog.Status status);

    // Metodo personalizzato per aggiornare lo stato di un RequestLog
    @Transactional
    @Modifying
    @Query("UPDATE RequestLog r SET r.status = :newStatus WHERE r.id = :id")
    void updateStatus(Integer id, RequestLog.Status newStatus);

    // Metodo per cercare un RequestLog per ID (già incluso in JpaRepository)
    // Optional<RequestLog> findById(Integer id);

    // Salvataggio e aggiornamento sono gestiti automaticamente da JpaRepository

    // Eliminazione per ID (già incluso in JpaRepository)
    // void deleteById(Integer id);
}

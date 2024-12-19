package com.TreenityBackend.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.StatusEntity;

import ch.qos.logback.core.status.Status;

@Repository
public interface StatusDAO extends JpaRepository<StatusEntity, Integer> {
	// Trova uno status per nome (es. RECEIVED, IN_PROGRESS, etc.)
    Optional<Status> findByName(StatusEntity.StatusName name);

    // Controlla se esiste uno status con un determinato nome
    boolean existsByName(StatusEntity.StatusName name);
}

package com.TreenityBackend.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.StatusEntity;


@Repository
public interface StatusEntityDAO extends JpaRepository<StatusEntity, Integer> {
	// Trova uno status per nome (es. RECEIVED, IN_PROGRESS, etc.)
    Optional<StatusEntity> findByName(StatusEntity.StatusName name);

    // Controlla se esiste uno status con un determinato nome
    boolean existsByName(StatusEntity.StatusName name);
}

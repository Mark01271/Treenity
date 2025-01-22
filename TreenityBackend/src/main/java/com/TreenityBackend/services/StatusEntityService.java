package com.TreenityBackend.services;

import com.TreenityBackend.entities.StatusEntity;

import java.util.List;
import java.util.Optional;

public interface StatusEntityService {
	//Recupera tutti gli stati disponibili in una lista.
    List<StatusEntity> getAllStatuses();
    // Recupera una status entity tramite il suo ID.
    Optional<StatusEntity> getStatusById(Integer id);
    // Recupera una status entity tramite il suo nome.
    Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name);
    // Salva una status entity.
    StatusEntity saveStatus(StatusEntity statusEntity);
}

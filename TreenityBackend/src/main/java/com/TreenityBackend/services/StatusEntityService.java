package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import com.TreenityBackend.entities.StatusEntity;

public interface StatusEntityService {
    List<StatusEntity> getAllStatuses();
    Optional<StatusEntity> getStatusById(Integer id);
    Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name);
    StatusEntity saveStatus(StatusEntity statusEntity);
}
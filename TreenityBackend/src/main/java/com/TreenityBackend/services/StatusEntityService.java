package com.TreenityBackend.services;

import com.TreenityBackend.entities.StatusEntity;

import java.util.List;
import java.util.Optional;

public interface StatusEntityService {
    List<StatusEntity> getAllStatuses();
    Optional<StatusEntity> getStatusById(Integer id);
    Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name);
    StatusEntity saveStatus(StatusEntity statusEntity);
}

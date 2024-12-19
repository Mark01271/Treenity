package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.repos.StatusDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusEntityServiceImpl implements StatusEntityService {
    private final StatusDAO statusDao;

    @Override
    public List<StatusEntity> getAllStatuses() {
        return statusDao.findAll();
    }

    @Override
    public Optional<StatusEntity> getStatusById(Integer id) {
        return statusDao.findById(id);
    }

    @Override
    public Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name) {
        return statusDao.findByName(name);
    }

    @Override
    public StatusEntity saveStatus(StatusEntity statusEntity) {
        return statusDao.save(statusEntity);
    }
}

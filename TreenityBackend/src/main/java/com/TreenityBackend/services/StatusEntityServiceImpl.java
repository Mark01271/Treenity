package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.repos.StatusEntityDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusEntityServiceImpl implements StatusEntityService {
    private final StatusEntityDAO statusEntityDao;

    @Override
    public List<StatusEntity> getAllStatuses() {
        return statusEntityDao.findAll();
    }

    @Override
    public Optional<StatusEntity> getStatusById(Integer id) {
        return statusEntityDao.findById(id);
    }

    @Override
    public Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name) {
        return statusEntityDao.findByName(name);
    }

    @Override
    public StatusEntity saveStatus(StatusEntity statusEntity) {
        return statusEntityDao.save(statusEntity);
    }
}

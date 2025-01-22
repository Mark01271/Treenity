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

    //Recupera tutti gli stati disponibili in una lista.
    @Override
    public List<StatusEntity> getAllStatuses() {
        return statusEntityDao.findAll();
    }

    // Recupera una status entity tramite il suo ID.
    @Override
    public Optional<StatusEntity> getStatusById(Integer id) {
        return statusEntityDao.findById(id);
    }

    // Recupera una status entity tramite il suo nome.
    @Override
    public Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name) {
        return statusEntityDao.findByName(name);
    }

    // Salva una status entity.
    @Override
    public StatusEntity saveStatus(StatusEntity statusEntity) {
        return statusEntityDao.save(statusEntity);
    }
}

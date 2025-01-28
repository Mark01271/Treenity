package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.repos.StatusEntityDAO;

import lombok.RequiredArgsConstructor;

/**
 * Implementazione del servizio per la gestione degli Stati delle Entità.
 * Questo servizio consente di ottenere, salvare e gestire gli stati (StatusEntity) delle richieste nel sistema.
 */
@Service
@RequiredArgsConstructor
public class StatusEntityServiceImpl implements StatusEntityService {
    
    private final StatusEntityDAO statusEntityDao;

    /**
     * Recupera tutti gli stati disponibili nel sistema.
     * 
     * @return Una lista di tutti gli stati presenti nel sistema
     */
    @Override
    public List<StatusEntity> getAllStatuses() {
        // Restituisce tutti gli stati presenti nel database
        return statusEntityDao.findAll();
    }

    /**
     * Recupera uno stato specifico tramite il suo ID.
     * 
     * @param id L'ID dello stato da recuperare
     * @return Un oggetto Optional contenente lo StatusEntity, se trovato
     */
    @Override
    public Optional<StatusEntity> getStatusById(Integer id) {
        // Recupera lo stato tramite il suo ID
        return statusEntityDao.findById(id);
    }

    /**
     * Recupera uno stato specifico tramite il suo nome.
     * 
     * @param name Il nome dello stato da recuperare
     * @return Un oggetto Optional contenente lo StatusEntity, se trovato
     */
    @Override
    public Optional<StatusEntity> getStatusByName(StatusEntity.StatusName name) {
        // Recupera lo stato tramite il suo nome
        return statusEntityDao.findByName(name);
    }

    /**
     * Salva uno stato (StatusEntity) nel sistema.
     * 
     * @param statusEntity Lo stato da salvare
     * @return Lo StatusEntity salvato
     */
    @Override
    public StatusEntity saveStatus(StatusEntity statusEntity) {
        // Salva lo stato nel database
        return statusEntityDao.save(statusEntity);
    }
}

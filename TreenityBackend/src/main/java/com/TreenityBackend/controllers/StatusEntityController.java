package com.TreenityBackend.controllers;

import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.services.StatusEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller per la gestione degli Stati (StatusEntity).
 * Permette di eseguire operazioni CRUD (Create, Read, Update) sugli Stati.
 */
@RestController
@RequestMapping("/status")  
public class StatusEntityController {

    @Autowired
    private StatusEntityService statusEntityService;

    /**
     * Endpoint per ottenere tutti gli Stati.
     * 
     * @return Una lista di tutti gli Stati
     */
    @GetMapping("/all")
    public List<StatusEntity> getAllStatuses() {
        return statusEntityService.getAllStatuses();  // Ottiene e restituisce tutti gli Stati
    }

    /**
     * Endpoint per ottenere uno Stato specifico tramite ID.
     * 
     * @param id L'ID dello Stato da recuperare
     * @return Un oggetto StatusEntity se trovato, altrimenti un oggetto Optional vuoto
     */
    @GetMapping("/{id}")
    public Optional<StatusEntity> getStatusById(@PathVariable Integer id) {
        return statusEntityService.getStatusById(id);  // Cerca lo Stato tramite ID e lo restituisce come Optional
    }

    /**
     * Endpoint per ottenere uno Stato tramite il nome.
     * 
     * @param name Il nome dello Stato da recuperare
     * @return Un oggetto StatusEntity se trovato, altrimenti un oggetto Optional vuoto
     */
    @GetMapping("/name/{name}")
    public Optional<StatusEntity> getStatusByName(@PathVariable StatusEntity.StatusName name) {
        return statusEntityService.getStatusByName(name);  // Cerca lo Stato tramite il nome e lo restituisce come Optional
    }

    /**
     * Endpoint per creare un nuovo Stato.
     * 
     * @param statusEntity L'oggetto StatusEntity da creare
     * @return Lo Stato appena creato
     */
    @PostMapping
    public StatusEntity saveStatus(@RequestBody StatusEntity statusEntity) {
        return statusEntityService.saveStatus(statusEntity);  // Salva il nuovo Stato e lo restituisce
    }

    /**
     * Endpoint per aggiornare uno Stato esistente tramite ID.
     * 
     * @param id L'ID dello Stato da aggiornare
     * @param statusEntity Oggetto StatusEntity con i nuovi dati
     * @return Lo Stato aggiornato
     */
    @PutMapping("/update/{id}")
    public StatusEntity updateStatus(@PathVariable Integer id, @RequestBody StatusEntity statusEntity) {
        statusEntity.setId(id);  // Imposta l'ID dello Stato che deve essere aggiornato
        return statusEntityService.saveStatus(statusEntity);  // Salva l'aggiornamento dello Stato e restituisce il risultato
    }
}

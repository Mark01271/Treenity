package com.TreenityBackend.controllers;

import com.TreenityBackend.entities.StatusEntity;
import com.TreenityBackend.services.StatusEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status")
public class StatusEntityController {

    @Autowired
    private StatusEntityService statusEntityService;

    // Get all Statuses
    @GetMapping("/all")
    public List<StatusEntity> getAllStatuses() {
        return statusEntityService.getAllStatuses();
    }

    // Get Status by ID
    @GetMapping("/{id}")
    public Optional<StatusEntity> getStatusById(@PathVariable Integer id) {
        return statusEntityService.getStatusById(id);
    }

    // Get Status by Name
    @GetMapping("/name/{name}")
    public Optional<StatusEntity> getStatusByName(@PathVariable StatusEntity.StatusName name) {
        return statusEntityService.getStatusByName(name);
    }

    // Create a new Status
    @PostMapping
    public StatusEntity saveStatus(@RequestBody StatusEntity statusEntity) {
        return statusEntityService.saveStatus(statusEntity);
    }

    // Update Status by ID
    @PutMapping("/update/{id}")
    public StatusEntity updateStatus(@PathVariable Integer id, @RequestBody StatusEntity statusEntity) {
        statusEntity.setId(id);
        return statusEntityService.saveStatus(statusEntity);
    }
}

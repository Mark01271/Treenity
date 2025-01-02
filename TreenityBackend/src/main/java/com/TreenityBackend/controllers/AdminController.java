package com.TreenityBackend.controllers;

import com.TreenityBackend.config.PasswordChangeRequest;
import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.exceptions.ResourceNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.services.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Ottieni tutti gli admin
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Ottieni Admin tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + id + " not found."));
    }

    // Crea un nuovo Admin
    @PostMapping
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = adminService.saveAdmin(admin);
            return ResponseEntity.ok(savedAdmin);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Aggiorna un Admin tramite ID
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        try {
            admin.setId(id);
            Admin updatedAdmin = adminService.saveAdmin(admin);
            return ResponseEntity.ok(updatedAdmin);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found.");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Elimina un Admin tramite ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok("Admin deleted successfully.");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " does not exist.");
        }
    }

    // Cambia la password dell'Admin
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Integer id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        if (passwordChangeRequest.getOldPassword() == null || passwordChangeRequest.getNewPassword() == null) {
            return ResponseEntity.badRequest().body("Both old and new passwords must be provided.");
        }

        try {
            String response = adminService.changePassword(id, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found.");
        }
    }
}

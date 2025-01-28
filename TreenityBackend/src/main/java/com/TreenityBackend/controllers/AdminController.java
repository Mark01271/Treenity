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

/**
 * Controller per la gestione degli amministratori (Admin).
 * Permette di eseguire operazioni CRUD (Create, Read, Update, Delete) sugli amministratori.
 * Include anche un'operazione per cambiare la password di un amministratore.
 */
@RestController
@RequestMapping("/admin")  
@RequiredArgsConstructor  
public class AdminController {

    private final AdminService adminService;

    /**
     * Recupera tutti gli amministratori.
     * 
     * @return La lista degli amministratori
     */
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();  // Ottiene la lista di tutti gli amministratori
        return ResponseEntity.ok(admins);  // Restituisce la lista con stato HTTP 200 OK
    }

    /**
     * Recupera un amministratore specifico tramite ID.
     * 
     * @param id L'ID dell'amministratore da recuperare
     * @return L'amministratore con l'ID specificato
     * @throws ResourceNotFoundException Se l'amministratore con l'ID specificato non viene trovato
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id)  // Cerca l'amministratore tramite ID
                .map(ResponseEntity::ok)  // Se trovato, restituisce l'amministratore con stato 200 OK
                .orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + id + " not found."));  // Se non trovato, lancia un'eccezione
    }

    /**
     * Crea un nuovo amministratore.
     * 
     * @param admin L'amministratore da creare
     * @return L'amministratore appena creato
     * @throws ValidationException Se la richiesta non è valida
     */
    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = adminService.saveAdmin(admin);  // Salva l'amministratore nel sistema
            return ResponseEntity.ok(savedAdmin);  // Restituisce l'amministratore salvato con stato 200 OK
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);  // Se la validazione fallisce, restituisce stato 400 Bad Request
        }
    }

    /**
     * Aggiorna un amministratore esistente tramite ID.
     * 
     * @param id L'ID dell'amministratore da aggiornare
     * @param admin L'amministratore con i dati aggiornati
     * @return L'amministratore aggiornato
     * @throws ResourceNotFoundException Se l'amministratore con l'ID specificato non viene trovato
     * @throws ValidationException Se la richiesta di aggiornamento non è valida
     */
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        try {
            admin.setId(id);  // Imposta l'ID dell'amministratore da aggiornare
            Admin updatedAdmin = adminService.saveAdmin(admin);  // Salva l'amministratore aggiornato
            return ResponseEntity.ok(updatedAdmin);  // Restituisce l'amministratore aggiornato con stato 200 OK
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found.");  // Se l'amministratore non esiste, lancia un'eccezione
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);  // Se la validazione fallisce, restituisce stato 400 Bad Request
        }
    }

    /**
     * Elimina un amministratore tramite ID.
     * 
     * @param id L'ID dell'amministratore da eliminare
     * @return Messaggio di successo se l'eliminazione è riuscita
     * @throws ResourceNotFoundException Se l'amministratore con l'ID specificato non esiste
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer id) {
        try {
            adminService.deleteAdmin(id);  // Elimina l'amministratore con l'ID specificato
            return ResponseEntity.ok("Admin deleted successfully.");  // Restituisce un messaggio di successo con stato 200 OK
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " does not exist.");  // Se l'amministratore non esiste, lancia un'eccezione
        }
    }

    /**
     * Cambia la password di un amministratore.
     * 
     * @param id L'ID dell'amministratore
     * @param passwordChangeRequest Oggetto contenente la vecchia e la nuova password
     * @return Un messaggio di successo o errore in base al risultato
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Integer id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        // Verifica che siano presenti sia la vecchia che la nuova password
        if (passwordChangeRequest.getOldPassword() == null || passwordChangeRequest.getNewPassword() == null) {
            return ResponseEntity.badRequest().body("Both old and new passwords must be provided.");  // Restituisce errore se mancano le password
        }

        try {
            // Cambia la password tramite il servizio AdminService
            String response = adminService.changePassword(id, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
            return ResponseEntity.ok(response);  // Restituisce il messaggio di successo con stato 200 OK
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Restituisce il messaggio di errore con stato 400 Bad Request
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found.");  // Se l'amministratore non esiste, lancia un'eccezione
        }
    }
}

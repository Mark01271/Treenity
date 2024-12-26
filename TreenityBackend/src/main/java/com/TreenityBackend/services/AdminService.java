package com.TreenityBackend.services;

import java.util.List;
import java.util.Optional;

import com.TreenityBackend.entities.Admin;



// Dichiarazione dei metodi per la logica da applicare sugli admin
public interface AdminService {

	// Salva un nuovo admin
	Admin saveAdmin(Admin admin);
	
	// Controlla se esiste un admin con email
    boolean existsByEmail(String email);
    // Controlla se esiste un admin con username
    boolean existsByUsername(String username);
 
	// Trova admin in base alla sua mail 
	Optional<Admin> getAdminByEmail(String email);
	// Trova admin in base al suo username
	Optional<Admin> getAdminByUsername(String username); 
	// Trova admin in base al suo id 
	Optional<Admin> getAdminById(Integer id);
	
	// Trova tutti gli admin
    List<Admin> getAllAdmins();

    // Elimina un admin in base al suo id
    void deleteAdmin(Integer id);
}
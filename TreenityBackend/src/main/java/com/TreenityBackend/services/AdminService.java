package com.TreenityBackend.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.TreenityBackend.entities.Admin;

// Dichiarazione dei metodi per la logica da applicare sugli admin
public interface AdminService {

	// Registra un nuovo admin
	void registerAdmin(Admin admin);
 
	// Carica i dettagli dell'admin in base al nome
	UserDetails loadAdminByUsername(String Username) throws UsernameNotFoundException;
 
	// Trova admin in base al suo username
	Admin findByUsername(String username); 
	// Trova admin in base al suo id 
	Admin getAdminById(Integer id);
	
	// Aggiorna email admin
	boolean updateEmail(Integer id, String newEmail, String password);
	
	// Aggiorna password admin
	boolean updatePassword(Integer id, String newPassword, String currentPassword);
	
}
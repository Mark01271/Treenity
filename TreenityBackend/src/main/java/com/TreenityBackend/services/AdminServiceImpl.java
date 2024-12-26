package com.TreenityBackend.services;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.repos.AdminDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	private AdminDAO adminDAO; 
	// Utilizzo l'iniezione del costruttore (lombok lo fa per noi con @RequiredArgsConstructor)

    @Override
    // Salva un nuovo admin
    public Admin saveAdmin(Admin admin) {
        return adminDAO.save(admin);
    }
 	
    @Override
 	// Controlla se esiste un admin con email
    public boolean existsByEmail(String email) {
        return adminDAO.existsByEmail(email);
    }
    
    @Override
    // Controlla se esiste un admin con username
    public boolean existsByUsername(String username) {
        return adminDAO.existsByUsername(username);
    }
  
    @Override
 	// Trova admin in base alla sua mail 
    public Optional<Admin> getAdminByEmail(String email) {
        return adminDAO.findByEmail(email);
    }
    
    @Override
 	// Trova admin in base al suo username
    public Optional<Admin> getAdminByUsername(String username) {
        return adminDAO.findByUsername(username);
    }
    
    @Override
 	// Trova admin in base al suo id 
    public Optional<Admin> getAdminById(Integer id) {
        return adminDAO.findById(id);
    }
 	
    @Override
    // Utilizzo il metodo del repository per ottenere tutti gli Admin
    public List<Admin> getAllAdmins() {
        return adminDAO.findAll();
    }

    @Override
    // Utilizzo il metodo del repository per eliminare un Admin per ID
    public void deleteAdmin(Integer id) {
        adminDAO.deleteById(id);
    }

}
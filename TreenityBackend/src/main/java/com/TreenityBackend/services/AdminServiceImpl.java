package com.TreenityBackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.repos.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

    @Autowired
    private AdminDAO adminDAO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    // Carica admin in base al nome per l'autenticazione, se esistente
    @Override
    public UserDetails loadAdminByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentativo di login con: " + username);
        /*
        Optional<Admin> adminOpt = AdminDAO.findByUsername(username);

        if(adminOpt.isPresent()) { //controlla se l`utente è presente
            Admin admin = adminOpt.get();
            System.out.println("Utente trovato: " + admin.getUsername());
            return new org.springframework.security.core.userdetails.Admin(admin.getUsername(), admin.getPassword(), new ArrayList<>());
        } else {
            System.out.println("Utente non trovato con username: " + username);
            throw new UsernameNotFoundException("Utente non trovato con username o email: " + username);
        }
        */
    }
    
    // Registra nuovo utente controllando se username o email esistono già
    public void registerAdmin(Admin admin) {
    	// Controlla se l'admin è già in uso
        if (adminDAO.findByUsername(admin.getUsername()).isPresent()) { 
            throw new IllegalArgumentException("Username già esistente.");
        }

     // Controlla se la mail è già in uso
        if (adminDAO.findByEmail(admin.getEmail()).isPresent()) { 
            throw new IllegalArgumentException("Email già esistente.");
        }

     // Cifra la password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));  
        adminDAO.save(admin);  // Salva admin nel database
    }
    
    // Trova admin in base al nome
	@Override
	public Admin findByUsername(String username) {
		return adminDAO.findByUsername(username).get();
	}

    // Recupera admin in base al suo ID
    @Override
    public Admin getAdminById(Integer id) {
        return adminDAO.findById(id).orElse(null);
    }

    // Aggiorna l'email di un admin se la password corrente corrisponde
    @Override
    public boolean updateEmail(Integer id, String newEmail, String password) {
        Admin admin = getAdminById(id);
      // Controlla se la password criptata è uguale a quella salvata nel database
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            admin.setEmail(newEmail);
            adminDAO.save(admin);
            // Segnala se l`operazione ha avuto successo restituendo true
            return true;
        }
      // Segnala se l`operazione è fallita restituendo false
        else {
        	return false;
        }
    }

    // Aggiorna la password dell'admin se la password corrente corrisponde
    @Override
    public boolean updatePassword(Integer id, String newPassword, String currentPassword) {
        Admin admin = getAdminById(id);
        //controlla se la password criptata è uguale a quella salvata nel database
        if (admin != null && passwordEncoder.matches(currentPassword, admin.getPassword())) {
            admin.setPassword(passwordEncoder.encode(newPassword));
            adminDAO.save(admin);
            //segnala se l`operazione ha avuto successo restituendo true
            return true;
        }
        //segnala se l`operazione è fallita restituendo false
        else {
        	return false;
        }
    }

}
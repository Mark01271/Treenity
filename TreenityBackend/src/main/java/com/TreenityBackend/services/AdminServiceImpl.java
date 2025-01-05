package com.TreenityBackend.services;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.exceptions.ResourceNotFoundException;
import com.TreenityBackend.exceptions.ValidationException;
import com.TreenityBackend.repos.AdminDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;
    private final PasswordEncoder passwordEncoder;

    // Validazione della complessità della password
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8 || 
            !Pattern.compile("[A-Z]").matcher(password).find() || 
            !Pattern.compile("[a-z]").matcher(password).find() || 
            !Pattern.compile("[0-9]").matcher(password).find() || 
            !Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return false; // La password deve rispettare tutte le condizioni
        }
        return true;
    }

    // Validazione del formato dell'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        // Validazione dell'username univoco
        if (adminDAO.existsByUsername(admin.getUsername())) {
            throw new ValidationException("Username is already taken.");
        }

        // Validazione dell'email unica e formato valido
        if (adminDAO.existsByEmail(admin.getEmail())) {
            throw new ValidationException("Email is already in use.");
        }
        if (!isValidEmail(admin.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

        // Validazione della password
        if (!isValidPassword(admin.getPasswordHash())) {
            throw new ValidationException("Password must be at least 8 characters long, contain uppercase, lowercase, digit, and a special character.");
        }

        // Hashing della password
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));

        return adminDAO.save(admin);
    }

    @Override
    public boolean existsByEmail(String email) {
        return adminDAO.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminDAO.existsByUsername(username);
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminDAO.findByEmail(email)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with email " + email + " not found.");
                });
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminDAO.findByUsername(username)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with username " + username + " not found.");
                });
    }

    @Override
    public Optional<Admin> getAdminById(Integer id) {
        return adminDAO.findById(id)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with ID " + id + " not found.");
                });
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminDAO.findAll();
    }

    @Override
    public void deleteAdmin(Integer id) {
        if (!adminDAO.existsById(id)) {
            throw new ResourceNotFoundException("Admin with ID " + id + " does not exist.");
        }
        adminDAO.deleteById(id);
    }

    @Override
    public String changePassword(Integer id, String oldPassword, String newPassword) {
        Admin admin = adminDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + id + " not found."));

        // Verifica della vecchia password
        if (!passwordEncoder.matches(oldPassword, admin.getPasswordHash())) {
            throw new ValidationException("Old password is incorrect.");
        }

        // Validazione della nuova password
        if (!isValidPassword(newPassword)) {
            throw new ValidationException("Password must be at least 8 characters long, contain uppercase, lowercase, digit, and a special character.");
        }

        // Hashing della nuova password
        admin.setPasswordHash(passwordEncoder.encode(newPassword));
        adminDAO.save(admin);
        return "Password changed successfully.";
    }
}

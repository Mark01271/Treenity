package com.TreenityBackend.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.Admin;
import com.TreenityBackend.repos.AdminDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminDAO adminDao;

    // Recupera tutti gli admin
    public List<Admin> getAllAdmins() {
        return adminDao.findAll();
    }

    // Trova un admin per ID
    public Optional<Admin> getAdminById(Integer id) {
        return adminDao.findById(id);
    }

    // Trova un admin per username
    public Optional<Admin> getAdminByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    // Salva un nuovo admin
    public Admin saveAdmin(Admin admin) {
        return adminDao.save(admin);
    }

    // Cancella un admin per ID
    public void deleteAdminById(Integer id) {
        adminDao.deleteById(id);
    }
}

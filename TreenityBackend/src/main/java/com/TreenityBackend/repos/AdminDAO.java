package com.TreenityBackend.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.Admin;

@Repository
public interface AdminDAO extends JpaRepository<Admin, Integer> {
    // Trova un Admin per username
    Optional<Admin> findByUsername(String username);

    // Trova un Admin per email
    Optional<Admin> findByEmail(String email);

    // Controlla se esiste un Admin con username
    boolean existsByUsername(String username);

    // Controlla se esiste un Admin con email
    boolean existsByEmail(String email);
}

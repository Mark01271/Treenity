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

/**
 * Implementazione del servizio di gestione degli amministratori (Admin).
 * Questo servizio include la gestione degli amministratori, la validazione delle informazioni
 * come username, email, password e la gestione delle operazioni di modifica, eliminazione e recupero degli admin.
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;
    private final PasswordEncoder passwordEncoder;

    /**
     * Funzione di validazione della password.
     * La password deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola, una minuscola,
     * un numero e un carattere speciale.
     *
     * @param password La password da validare
     * @return true se la password è valida, false altrimenti
     */
    private boolean isValidPassword(String password) {
        // Verifica la lunghezza e la presenza dei vari caratteri (maiuscole, minuscole, numeri, caratteri speciali)
        if (password == null || password.length() < 8 || 
            !Pattern.compile("[A-Z]").matcher(password).find() || 
            !Pattern.compile("[a-z]").matcher(password).find() || 
            !Pattern.compile("[0-9]").matcher(password).find() || 
            !Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return false; // La password deve rispettare tutte le condizioni
        }
        return true;
    }

    /**
     * Funzione di validazione del formato dell'email.
     * Verifica se l'email fornita segue il formato corretto.
     *
     * @param email L'email da validare
     * @return true se l'email è valida, false altrimenti
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";  // Espressione regolare per validare l'email
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    /**
     * Salva un nuovo amministratore dopo aver validato i suoi dati (username, email, password).
     *
     * @param admin L'amministratore da salvare
     * @return L'amministratore salvato
     * @throws ValidationException Se l'username o l'email sono già in uso, o la password non è valida
     */
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

        // Hashing della password prima di salvarla nel database
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));

        // Salva e restituisce l'amministratore
        return adminDAO.save(admin);
    }

    /**
     * Verifica se esiste un amministratore con l'email specificata.
     *
     * @param email L'email da cercare
     * @return true se esiste un amministratore con quella email, altrimenti false
     */
    @Override
    public boolean existsByEmail(String email) {
        return adminDAO.existsByEmail(email);
    }

    /**
     * Verifica se esiste un amministratore con il nome utente specificato.
     *
     * @param username Il nome utente da cercare
     * @return true se esiste un amministratore con quel nome utente, altrimenti false
     */
    @Override
    public boolean existsByUsername(String username) {
        return adminDAO.existsByUsername(username);
    }

    /**
     * Trova un amministratore tramite la sua email.
     *
     * @param email L'email dell'amministratore da cercare
     * @return Un oggetto Optional contenente l'amministratore trovato, o un'eccezione se non trovato
     * @throws ResourceNotFoundException Se l'amministratore non esiste con l'email specificata
     */
    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminDAO.findByEmail(email)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with email " + email + " not found.");
                });
    }

    /**
     * Trova un amministratore tramite il suo nome utente.
     *
     * @param username Il nome utente dell'amministratore da cercare
     * @return Un oggetto Optional contenente l'amministratore trovato, o un'eccezione se non trovato
     * @throws ResourceNotFoundException Se l'amministratore non esiste con il nome utente specificato
     */
    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminDAO.findByUsername(username)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with username " + username + " not found.");
                });
    }

    /**
     * Trova un amministratore tramite il suo ID.
     *
     * @param id L'ID dell'amministratore da cercare
     * @return Un oggetto Optional contenente l'amministratore trovato, o un'eccezione se non trovato
     * @throws ResourceNotFoundException Se l'amministratore non esiste con l'ID specificato
     */
    @Override
    public Optional<Admin> getAdminById(Integer id) {
        return adminDAO.findById(id)
                .or(() -> {
                    throw new ResourceNotFoundException("Admin with ID " + id + " not found.");
                });
    }

    /**
     * Recupera tutti gli amministratori.
     *
     * @return Una lista di tutti gli amministratori
     */
    @Override
    public List<Admin> getAllAdmins() {
        return adminDAO.findAll();
    }

    /**
     * Elimina un amministratore tramite il suo ID.
     *
     * @param id L'ID dell'amministratore da eliminare
     * @throws ResourceNotFoundException Se l'amministratore non esiste con l'ID specificato
     */
    @Override
    public void deleteAdmin(Integer id) {
        if (!adminDAO.existsById(id)) {
            throw new ResourceNotFoundException("Admin with ID " + id + " does not exist.");
        }
        adminDAO.deleteById(id);
    }

    /**
     * Cambia la password di un amministratore.
     * Verifica la vecchia password e valida la nuova password prima di aggiornarla.
     *
     * @param id L'ID dell'amministratore
     * @param oldPassword La vecchia password
     * @param newPassword La nuova password
     * @return Un messaggio di successo
     * @throws ValidationException Se la vecchia password non è corretta o la nuova password non è valida
     * @throws ResourceNotFoundException Se l'amministratore non esiste con l'ID specificato
     */
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
        adminDAO.save(admin);  // Salva l'amministratore con la nuova password
        return "Password changed successfully.";
    }
}

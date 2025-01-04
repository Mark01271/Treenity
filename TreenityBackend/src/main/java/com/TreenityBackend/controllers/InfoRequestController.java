package com.TreenityBackend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.entities.RequestLog;
import com.TreenityBackend.exceptions.InfoRequestNotFoundException;
import com.TreenityBackend.services.EmailService;
import com.TreenityBackend.services.InfoRequestService;
import com.TreenityBackend.services.RequestLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/info-requests")
@RequiredArgsConstructor
public class InfoRequestController {

    private final InfoRequestService infoRequestService;
    private final RequestLogService requestLogService;
    private final EmailService emailService;

    @PostMapping
    public InfoRequest createInfoRequest(@RequestBody InfoRequest request, @RequestParam Integer adminId) {
        RequestLog requestLog = requestLogService.createRequestLog(request, adminId);
        request.setRequestLog(requestLog);
        InfoRequest savedRequest = infoRequestService.saveInfoRequest(request);

        emailService.sendUserConfirmationEmail(request.getEmail());

        String requestDetails = "Nuova richiesta di informazioni:\n\n" +
                                "Gruppo: " + request.getGroupName() + "\n" +
                                "Contatto: " + request.getContactPerson() + "\n" +
                                "Email: " + request.getEmail() + "\n" +
                                "Telefono: " + request.getPhone();
        emailService.sendAdminNotificationEmail("admin@yourdomain.com", requestDetails);

        return savedRequest;
    }

    @GetMapping
    public List<InfoRequest> getAllInfoRequests() {
        return infoRequestService.getAllInfoRequests();
    }

    @GetMapping("/{id}")
    public InfoRequest getInfoRequestById(@PathVariable Integer id) {
        return infoRequestService.getInfoRequestById(id).orElse(null);
    }

    // Gestione delle eccezioni
    @ExceptionHandler(InfoRequestNotFoundException.class)
    public ResponseEntity<String> handleInfoRequestNotFound(InfoRequestNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

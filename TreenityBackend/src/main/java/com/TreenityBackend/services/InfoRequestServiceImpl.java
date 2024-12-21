package com.TreenityBackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.TreenityBackend.entities.InfoRequest;
import com.TreenityBackend.repos.InfoRequestDAO;

@Service
@RequiredArgsConstructor
public class InfoRequestServiceImpl implements InfoRequestService {

    private final InfoRequestDAO infoRequestDAO;
    
    // boh
    private final JavaMailSender mailSender;

    @Override
    public InfoRequest saveAndSendEmail(InfoRequest infoRequest) {
        // Salva nel database
        InfoRequest savedInfoRequest = infoRequestDAO.save(infoRequest);

        // Invia email
        inviaEmail(savedInfoRequest);

        return savedInfoRequest;
    }

    private void inviaEmail(InfoRequest infoRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("tua-email@esempio.com");
        mailMessage.setSubject("Nuova Richiesta di Informazioni");
        mailMessage.setText("Dettagli della richiesta:\n\n" +
                "data ");

        mailSender.send(mailMessage);
    }
}


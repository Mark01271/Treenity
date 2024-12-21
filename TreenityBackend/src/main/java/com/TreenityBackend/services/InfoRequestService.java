package com.TreenityBackend.services;

import org.springframework.transaction.annotation.Transactional;

import com.TreenityBackend.entities.InfoRequest;

public interface InfoRequestService {
    @Transactional
    InfoRequest saveAndSendEmail(InfoRequest infoRequest);
}

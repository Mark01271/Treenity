package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.InfoRequest;

@Repository
public interface InfoRequestDAO extends JpaRepository<InfoRequest, Integer> {
	//trova una info request basandosi sull'id
    List<InfoRequest> findByRequestLog_Id(Integer requestLogId);
    //trova una info request basandosi sul nome del gruppo
    List<InfoRequest> findByGroupName(String groupName);
    //trova tutte le info request nel quale il campo newsletter è stato impostato True
    List<InfoRequest> findByNewsletterTrue();
    //trova tutte le info request ordinate dalla data di creazione in ordine decrescente
    List<InfoRequest> findAllByOrderByCreatedAtDesc();
}

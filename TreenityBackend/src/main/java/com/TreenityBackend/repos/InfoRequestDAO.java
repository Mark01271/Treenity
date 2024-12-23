package com.TreenityBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TreenityBackend.entities.InfoRequest;

@Repository
public interface InfoRequestDAO extends JpaRepository<InfoRequest, Integer> {
    List<InfoRequest> findByRequestLog_Id(Integer requestLogId);
    List<InfoRequest> findByGroupName(String groupName);
    List<InfoRequest> findByNewsletterTrue();
    List<InfoRequest> findAllByOrderByCreatedAtDesc();
}

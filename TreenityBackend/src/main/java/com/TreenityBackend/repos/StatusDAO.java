package com.TreenityBackend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.status.Status;

@Repository
public interface StatusDAO extends JpaRepository<Status, Integer> {

}

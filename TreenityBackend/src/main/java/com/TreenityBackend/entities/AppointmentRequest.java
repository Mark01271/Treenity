package com.TreenityBackend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Appointment_Requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "request_log_id")
    private RequestLog requestLog;

    @Column(nullable = false)
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupType groupType;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String availabilityDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityTime availabilityTime;

    @Column(nullable = false)
    private String eventIntent;

    @Column(nullable = false)
    private String message;

    private String additionalRequests;

    @Column(nullable = false)
    private Boolean consentForm;
    
    @Builder.Default
    private Boolean newsletter = false;
    
    @Builder.Default
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum GroupType {
        SCUOLA, SCOUT, FAMIGLIA, GRUPPI_PARROCCHIALI, GRUPPO_EVENTI, ORGANIZZAZIONI, ALTRO
    }

    public enum AvailabilityTime {
        MATTINA, POMERIGGIO, GIORNATA
    }
}

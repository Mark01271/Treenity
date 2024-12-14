package com.TreenityBackend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "info_requests") // Specifica che la tabella del DB si chiama "info_requests"
public class InfoRequest {

    @Id
    @Column(name = "id") // ricorda che è auto increment
    private Long id;

    @Column(name = "request_log_id")
    private Long requestLogId;
    
    @Column(name = "group_name")
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_type")
    private GroupType groupType;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "event_intent")
    private String eventIntent;

    @Column(name = "message")
    private String message;

    @Column(name = "additional_requests")
    private String additionalRequests;

    @Column(name = "consent_form")
    private Boolean consentForm;

    @Column(name = "newsletter")
    private Boolean newsletter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // Enum per group_type
    public enum GroupType {
        Scuola, Scout, Famiglia, Gruppi_parrocchiali, Gruppo_eventi, Organizzazioni, Altro
    }

    // Enum per status
    public enum Status {
        received, in_progress, completed, archived
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // getters e setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequestLogId() {
		return requestLogId;
	}

	public void setRequestLogId(Long requestLogId) {
		this.requestLogId = requestLogId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEventIntent() {
		return eventIntent;
	}

	public void setEventIntent(String eventIntent) {
		this.eventIntent = eventIntent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAdditionalRequests() {
		return additionalRequests;
	}

	public void setAdditionalRequests(String additionalRequests) {
		this.additionalRequests = additionalRequests;
	}

	public Boolean getConsentForm() {
		return consentForm;
	}

	public void setConsentForm(Boolean consentForm) {
		this.consentForm = consentForm;
	}

	public Boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}

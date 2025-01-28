manca/da completare:
tecnologie utilizzate (frontend)
esecuzione

# Treenity - Progetto "A caccia di un'idea"

## Obiettivo

Creazione di un portale che faccia da vetrina per Cascina Caccia, con l'obiettivo di rilanciare le visite scolastiche ed educative, fornire informazioni sugli eventi e promuovere le attività della struttura.
Il portale dovrà includere due form principali per facilitare l’interazione con gruppi interessati alle attività educative:<br>

1. Un form per la richiesta di informazioni, dove i visitatori potranno inserire i propri dati e domande per ottenere dettagli sulle attività offerte dalla Cascina.
2. Un form per la richiesta di appuntamento, pensato per consentire ai gruppi di proporre un periodo di disponibilità per organizzare una visita.
   Per rendere il flusso di lavoro più efficiente, sarà implementata una sezione dedicata agli amministratori, accessibile tramite login. Questa area consentirà di visualizzare tutte le richieste ricevute.<br>
   Gli amministratori potranno gestire il ciclo vitale delle richieste, aggiornando lo stato man mano che vengono prese in carico o concluse.

<br>

## Richiesta

Sviluppare i servizi backend necessari a:<br>

- Gestire le richieste di informazioni e appuntamenti, inviando una mail di conferma automatica ai richiedenti e notifiche agli amministratori.
- Registrare amministratori e permettere loro l'accesso all'area riservata dove potranno visualizzare le richieste e modificarne lo stato.

<br>
<hr>

# Tecnologie Utilizzate

## Backend

- Java
- Spring Boot (framework Java)
- Lombok (libreria Java)

## Frontend

- HTML
- Javascript e typescript
- CSS
- Bulma (come libreria CSS)

## Database

- MariaDB

<br>
<hr>

# Struttura del database

Il database utilizzato per il progetto si chiama `treenity` ed è progettato per poter gestire relazioni tra amministratori, i vari form form e i loro stati.

## Tabelle

1. **appointment_requests**

   - Contiene le informazioni relative alle richieste di appuntamenti.
   - Ogni richiesta è identificata da un id di tipo INT.
   - La tabella è relazionata alle tabelle `request_logs` tramite `request_log_id` (appointment_requests) e `id` (request_logs) e a `statuses` tramite `status_id` (appointment_requests) e `id` (statuses).
   - Alcuni attributi sono: `availability_date` (TEXT), `availability_time` (ENUM), `group_name` (VARCHAR), `group_type` (ENUM), `email`(VARCHAR), `phone` (VARCHAR), `message` (TEXT), `event_intent` (VARCHAR).

2. **info_requests**

   - Contiene le informazioni relative alle richieste di informazioni.
   - Ogni richiesta è identificata da un id di tipo INT.
   - La tabella è relazionata alle tabelle `request_logs` tramite `request_log_id` (info_requests) e `id` (request_logs) e a `statuses` tramite `status_id` (info_requests) e `id` (statuses).
   - Alcuni attributi sono: `message` (TEXT), `email`(VARCHAR), `phone` (VARCHAR), `event_intent` (VARCHAR).

3. **request_logs**

   - Contiene le informazioni relative ai log delle richieste.
   - Ogni log è identificato da un id di tipo INT.
   - La tabella è relazionata alle tabelle `appointment_requests` e `info_requests` tramite `request_log_id` (info_requests e appointment_requests) e `id` (request_logs), a `admins` tramite `updated_by` (request_logs) e `id` (admins) e a `statuses` tramite `id` (statuses) e `status_id` (request_logs).
   - Gli altri attributi sono: `related_request_id` (INT), `comment` (TEXT) e `updated_at` (TIMESTAMP).

4. **statuses**

   - Contiene i vari tipi di stati (ENUM).
   - Ogni tipo è identificato da un id di tipo INT.
   - La tabella è relazionata alle tabelle `appointment_requests`, `info_requests` e `request_logs` tramite `id` (statuses) e `status_id` (le altre tabelle).

5. **admins**
   - Contiene le informazioni relative agli amministartori.
   - Ogni amministartore è identificato da un id di tipo INT.
   - La tabella è relazionata alla tabella `request_logs` tramite `updated_by` (request_logs) e `id` (admins).
   - Gli altri attributi sono: `username` (VARCHAR), `password_hash` (VARCHAR), `email` (VARCHAR) e `created_at` (TIMESTAMP).

# Struttura del progetto Spring Boot

Il progetto è organizzato a "livelli": ogni livello svolge una funzionalità ben definita.

## Entities

Le entità rappresentano le tabelle del database. Il loro obiettivo è quello di descrivere attraverso attributi e metodi le tabelle e le relazioni del DB.

### **AppointmentRequest**

Rappresenta il form di richiesta appuntamento:

- id
- requestLog
- groupName
- groupType
- contactPerson
- email
- phone
- availabilityDate
- availabilityTime
- eventIntent
- message
- additionalRequest
- consentForm
- newsletter
- status
- createdAt

### **InfoRequest**

Rappresenta il form di richiesta informazioni:

- id
- requestLog
- groupName
- groupType
- contactPerson
- email
- phone
- eventIntent
- message
- additionalRequest
- consentForm
- newsletter
- status
- createdAt

### **RequestLog**

Rappresenta il log delle richieste:

- id
- updatedBy
- status
- comment
- updatedAt
- relatedRequestId

### **StatusEntity**

Rappresenta i diversi stati che può avere una richiesta:

- id
- name

### **Admin**

Rappresenta un amministratore registrato alla piattaforma:

- id
- username
- passwordHash
- email
- createdAt

<br>
<hr>

## Repos (DAO)

Le interfacce DAO (Data Access Object) sono utilizzate per accedere al database. Ogni entità ha un repository dedicato per semplificare le operazioni di ricerca e modifica dei dati del database.

### **AppointmentRequestDAO**

- findByRequestLog_Id: trova una l'appointment request contenente una request log dato l'id.
- findByAvailabilityDateContaining: controlla se la data inserita è disponibile per una visita .
- findByAvailabilityTime: controlla se l'orario inserito è disponibile per una visita.
- findAllByOrderByCreatedAtDesc: trova tutti gli appuntamenti ordinati dalla data di creazione in ordine decrescente.

### **InfoRequestDAO**

- findByRequestLog_Id: trova una info request basandosi sull'id.
- findByGroupName: trova una info request basandosi sul nome del gruppo.
- findByNewsletterTrue: trova tutte le info request nel quale il campo newsletter è stato impostato True.
- findAllByOrderByCreatedAtDesc: trova tutte le info request ordinate dalla data di creazione in ordine decrescente.

### **RequestLogDAO**

- findByUpdatedBy_Id: Trova i log aggiornati da uno specifico Admin.
- findByStatus_Id: Trova i log con uno specifico stato.
- findAllByOrderByUpdatedAtDesc: Ordina i log per data di aggiornamento decrescente

### **StatusEntityDAO**

- findByName: trova uno status per nome
- existsByName: controlla se esiste uno status con un determinato nome

### **AdminDAO**

- findByUsername: trova un amministratore in base al username fornito.
- findByEmail: trova un amministratore in base all'email fornita.
- existsByUsername: controlla se esiste un amministratore in base al username fornito.
- existsByEmail: controlla se esiste un amministratore in base all'email fornita.

<br>
<hr>

## Services

Contengono sostanzialmente la logica per il corretto funzionamento dell'applicazione. Vengono suddivisi in interfacce Service (dichiarazione dei metodi) e classi ServiceImpl (implementazione dei metodi). Questa separazione tra dichiarazione e implementazione ha vari benefici, in particolare per quanto riguarda la manutenibilità, testabilità, e scalabilità del codice.

### **AppointmentRequestService, AppointmentRequestServiceImpl**

Gestisce tutte le operazioni sulle richieste di appuntamenti.

### **InfoRequestService, InfoRequestServiceImpl**

Gestisce tutte le operazioni sulle richieste di informazioni.

### **RequestLogService, RequestLogServiceImpl**

Gestisce tutte le operazioni sui log delle richieste.

### **StatusEntityService, StatusEntityServiceImpl**

Gestisce tutte le operazioni sugli stati delle richieste.

### **AdminService, AdminServiceImpl**

Gestisce tutte le operazioni sugli amministratori.

### **EmailService, EmailServiceImpl**

Gestisce tutte le operazioni sulle email.

<br>
<hr>

## Controllers

Gestiscono le richieste HTTP e svolgono le operazioni dovute utilizzando le logiche dei service.

### **AppointmentRequestController**

Gestione delle chiamate per svolgere determinate operazioni sulle richieste di appuntamenti.

### **InfoRequestController**

Gestione delle chiamate per svolgere determinate operazioni sulle richieste di informazioni.

### **RequestLogController**

Gestione delle chiamate per svolgere determinate operazioni sui log delle richieste.

### **StatusEntityController**

Gestione delle chiamate per svolgere determinate operazioni sugli stati delle richieste.

### **AdminController**

Gestione delle chiamate per svolgere determinate operazioni sugli amministratori.

<br>
<hr>

## Configurazione

Il progetto contiene anche una classe per la configurazione degli utenti durante il processo di login e registrazione.

### **SecurityConfig**

Logica per codifica delle password degli amministratori e filtri di sicurezza e scrittura degli header.

<br>
<hr>

## Esecuzione del Progetto

1. **Requisiti**: assicurarsi di avere installato Java 11 o superiore, java springboot versione 3.4 o superiore, la libreria Lombok e di avere il database configurato correttamente.
2. **Configurazione**: verificare le configurazioni del database nel file `src/main/resources/application.properties`:

```java
spring.application.name=TreenityBackend // indica il nome del progetto

spring.datasource.url=jdbc:mariadb://localhost:3306/treenity // url del database

// impostazioni per il login al dbms
spring.datasource.username=root
spring.datasource.password=

// disabilita il DDL automatico
spring.jpa.hibernate.ddl-auto=none
```

3. **Avvio del server**: eseguire `localhost:8080` da un browser per poter avviare l'applicazione.

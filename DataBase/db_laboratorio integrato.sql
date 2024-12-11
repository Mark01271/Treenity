-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS treenity;
USE treenity;

-- Table for Admins
CREATE TABLE IF NOT EXISTS Admins (
  id INT PRIMARY KEY AUTO_INCREMENT, -- Unique identifier for each admin
  username VARCHAR(50) UNIQUE NOT NULL, -- Admin username
  password_hash VARCHAR(255) NOT NULL, -- Encrypted password for secure login
  email VARCHAR(100) UNIQUE NOT NULL, -- Admin email
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp when the admin account is created
);

-- Table for Info Requests
CREATE TABLE IF NOT EXISTS Info_Requests (
  id INT PRIMARY KEY AUTO_INCREMENT, -- Unique identifier for each request
  request_log_id INT NOT NULL, -- Foreign key to Request Logs
  group_name VARCHAR(100) NOT NULL, -- Name of the group submitting the request
  group_type ENUM('Scuola', 'Scout', 'Famiglia', 'Gruppi parrocchiali', 'Gruppo eventi', 'Organizzazioni', 'Altro') DEFAULT 'Altro', -- Type of group submitting the request
  contact_person VARCHAR(100) NOT NULL, -- Contact person's name
  email VARCHAR(100) NOT NULL, -- Contact email
  phone VARCHAR(15) NOT NULL, -- Phone number
  event_intent VARCHAR(255) NOT NULL, -- Intent of the event
  message TEXT NOT NULL, -- Additional details provided by the user
  additional_requests TEXT, -- Additional requests or requirements
  consent_form BOOLEAN NOT NULL, -- Indicates whether a consent form is provided
  newsletter BOOLEAN, -- Indicates subscription to the newsletter
  status ENUM('received', 'in_progress', 'completed', 'archived') DEFAULT 'received', -- Request status
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp of creation
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Last update timestamp
  FOREIGN KEY (request_log_id) REFERENCES Request_Logs(id) -- Link to Request Logs
);

-- Table for Appointment Requests
CREATE TABLE IF NOT EXISTS Appointment_Requests (
  id INT PRIMARY KEY AUTO_INCREMENT, -- Unique identifier for each request
  request_log_id INT NOT NULL, -- Foreign key to Request Logs
  group_name VARCHAR(100) NOT NULL, -- Name of the group submitting the request
  group_type ENUM('Scuola', 'Scout', 'Famiglia', 'Gruppi parrocchiali', 'Gruppo eventi', 'Organizzazioni', 'Altro') DEFAULT 'Altro', -- Type of group submitting the request
  contact_person VARCHAR(100) NOT NULL, -- Contact person's name
  email VARCHAR(100) NOT NULL, -- Contact email
  phone VARCHAR(15) NOT NULL, -- Phone number
  availability_date TEXT NOT NULL, -- Availability details for scheduling
  availability_time ENUM('Mattina', 'Pomeriggio', 'Gionata') NOT NULL, -- Time preference for availability
  event_intent VARCHAR(255) NOT NULL, -- Intent of the event
  message TEXT NOT NULL, -- Additional details provided by the user
  additional_requests TEXT, -- Additional requests or requirements
  consent_form BOOLEAN NOT NULL, -- Indicates whether a consent form is provided
  newsletter BOOLEAN, -- Indicates subscription to the newsletter
  status ENUM('received', 'in_progress', 'completed', 'archived') DEFAULT 'received', -- Request status
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp of creation
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Last update timestamp
  FOREIGN KEY (request_log_id) REFERENCES Request_Logs(id) -- Link to Request Logs
);

-- Table for Request Logs
CREATE TABLE IF NOT EXISTS Request_Logs (
  id INT PRIMARY KEY AUTO_INCREMENT, -- Unique identifier for each log entry
  updated_by INT NOT NULL, -- Admin responsible for the update
  status ENUM('received', 'in_progress', 'completed', 'archived') NOT NULL, -- Status at the time of update
  comment TEXT, -- Optional comment explaining the update
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp of the log entry
  FOREIGN KEY (updated_by) REFERENCES Admins(id) -- Link to the Admins table
);

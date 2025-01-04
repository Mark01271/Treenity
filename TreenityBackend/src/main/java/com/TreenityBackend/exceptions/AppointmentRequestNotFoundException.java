package com.TreenityBackend.exceptions;

public class AppointmentRequestNotFoundException extends RuntimeException {
    public AppointmentRequestNotFoundException(String message) {
        super(message);
    }
}

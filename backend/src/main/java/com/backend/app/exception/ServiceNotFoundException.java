package com.backend.app.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String id) {
        super("Service not found with id: " + id);
    }
}

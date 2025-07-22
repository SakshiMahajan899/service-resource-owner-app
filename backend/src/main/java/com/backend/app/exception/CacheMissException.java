package com.backend.app.exception;

/**
 * Exception thrown when a Service entity is not found in the cache.
 */
public class CacheMissException extends RuntimeException {
    public CacheMissException(String id) {
        super("Cache does not contain Service with id: " + id);
    }
}


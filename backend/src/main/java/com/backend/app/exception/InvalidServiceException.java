package com.backend.app.exception;


/**
 * Custom exception thrown when a {@link com.backend.app.model.Service} object is invalid
 * or violates business constraints such as missing ID, duplicate entries, etc.
 */
public class InvalidServiceException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidServiceException} with a detailed message.
     *
     * @param message the detail message explaining the validation failure
     */
    public InvalidServiceException(String message) {
        super(message);
    }
}

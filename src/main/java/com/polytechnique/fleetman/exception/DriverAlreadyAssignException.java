package com.polytechnique.fleetman.exception;

public class DriverAlreadyAssignException extends RuntimeException {
    public DriverAlreadyAssignException(String message) {
        super(message);
    }
}
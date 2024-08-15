package com.example.studentcrud.exception;

public class InvalidDomainException extends RuntimeException {
    public InvalidDomainException(String domain) {
        super("Invalid domain: " + domain);
    }
}

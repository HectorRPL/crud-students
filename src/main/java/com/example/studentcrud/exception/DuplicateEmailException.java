package com.example.studentcrud.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("A student with email " + email + " already exists.");
    }
}

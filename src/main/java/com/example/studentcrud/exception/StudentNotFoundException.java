package com.example.studentcrud.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Student with ID " + id + " not found");
    }
}
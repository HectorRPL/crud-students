package com.example.studentcrud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.studentcrud.exception.DuplicateEmailException;
import com.example.studentcrud.exception.InvalidDomainException;
import com.example.studentcrud.exception.StudentNotFoundException;
import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    private static final String DOMAIN_REGEX = "^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByEmailDomain(String domain) {
        logger.debug("Fetching students with email domain: {}", domain);

        if (domain == null || domain.trim().isEmpty() || !isValidDomain(domain)) {
            logger.warn("Invalid domain provided: {}", domain);
            throw new InvalidDomainException(domain);
        }

        List<Student> students = studentRepository.findAll().stream()
                .filter(student -> student.getEmail().endsWith(domain))
                .collect(Collectors.toList());

        logger.info("Found {} students with domain {}", students.size(), domain);
        return students;
    }

    public Optional<Student> getStudentById(Long id) {
        logger.debug("Fetching student with ID: {}", id);

        Optional<Student> studentOpt = studentRepository.findById(id);

        if (studentOpt.isPresent() && studentOpt.get() instanceof Student student) {
            logger.info("Student with ID {} found: {}", id, student);
            return Optional.of(student);
        } else {
            logger.warn("Student with ID {} not found", id);
            throw new StudentNotFoundException(id);
        }
    }

    public Student createStudent(@Valid Student student) {
        logger.info("Creating student with email: {}", student.getEmail());
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException(student.getEmail());
        }
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with ID: {}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    private boolean isValidDomain(String domain) {
        return domain.matches(DOMAIN_REGEX);
    }
}




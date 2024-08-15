package com.example.studentcrud.service;

import com.example.studentcrud.exception.DuplicateEmailException;
import com.example.studentcrud.exception.StudentNotFoundException;
import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private Validator validator;


    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGetStudentById_ThrowsStudentNotFoundException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(1L).orElseThrow(() -> new StudentNotFoundException(1L));
        });

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateStudent_ValidatesFirstNameAndLastNameLength() {
        Student student = new Student();
        student.setFirstName(""); // Vacío
        student.setLastName("Doe");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty());

        student.setFirstName("John");
        student.setLastName("DoeWithAVeryLongLastNameThatExceedsThirtyCharacters");

        violations = validator.validate(student);
        assertFalse(violations.isEmpty());

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testCreateStudent_ThrowsDuplicateEmailException() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        when(studentRepository.save(any(Student.class))).thenThrow(new DuplicateEmailException("john.doe@example.com"));

        assertThrows(DuplicateEmailException.class, () -> {
            studentService.createStudent(student);
        });

        verify(studentRepository, times(1)).save(student);
    }
}

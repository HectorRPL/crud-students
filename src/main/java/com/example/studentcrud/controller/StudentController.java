package com.example.studentcrud.controller;

import com.example.studentcrud.exception.StudentNotFoundException;
import com.example.studentcrud.model.Student;
import com.example.studentcrud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/domain/{domain}")
    public ResponseEntity<?> getStudentsByEmailDomain(@PathVariable(required = false) String domain) {
        System.out.println("domain = " + domain);
        if (domain == null || domain.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Domain parameter is required and cannot be empty.");
        }

        List<Student> students = studentService.getStudentsByEmailDomain(domain);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());

        Student updatedStudent = studentService.createStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}


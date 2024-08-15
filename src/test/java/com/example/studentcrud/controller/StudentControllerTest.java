package com.example.studentcrud.controller;

import com.example.studentcrud.exception.DuplicateEmailException;
import com.example.studentcrud.exception.GlobalExceptionHandler;
import com.example.studentcrud.model.Student;
import com.example.studentcrud.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreateStudent_ValidationErrors() throws Exception {
        // Test first name is empty
        String studentJson = "{\"firstName\": \"\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\"}";

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    // Verifica que el mensaje de error contenga cualquiera de las opciones válidas
                    assertTrue(
                            content.contains("First name must be between 1 and 30 characters") ||
                                    content.contains("First name is mandatory"),
                            "Validation error message for firstName is missing or incorrect."
                    );
                });

        // Test last name exceeds length
        studentJson = "{\"firstName\": \"John\", \"lastName\": \"DoeWithAVeryLongLastNameThatExceedsThirtyCharacters\", \"email\": \"john.doe@example.com\"}";

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"lastName\":\"Last name must be between 1 and 30 characters\"}"));
    }



    @Test
    void testCreateStudent_DuplicateEmailError() throws Exception {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        // Aquí estamos simulando que al intentar guardar un estudiante, se lanzará DuplicateEmailException
        when(studentService.createStudent(any(Student.class))).thenThrow(new DuplicateEmailException("john.doe@example.com"));

        String studentJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\"}";

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"error\":\"A student with email john.doe@example.com already exists.\"}"));
    }

}

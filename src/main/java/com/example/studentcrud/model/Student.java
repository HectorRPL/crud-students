package com.example.studentcrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max = 30, message = "Last name must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @jakarta.validation.constraints.Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

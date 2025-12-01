package com.example.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Allows subclasses like Student or Professor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role; // e.g., STUDENT, PROFESSOR, TA, ADMIN

    // Example: Answers provided by this user (if a TA or professor)
    @OneToMany(mappedBy = "responder")
    private List<Answer> answersGiven;

    // Getters and setters
}

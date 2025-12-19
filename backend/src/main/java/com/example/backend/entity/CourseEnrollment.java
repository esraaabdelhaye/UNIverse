package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One to Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Many-to-One to Course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate enrollmentDate;
    private String grade;
}

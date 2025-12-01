package com.example.backend.entity;

import com.example.backend.enums.QuestionStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // The actual question text

    @Enumerated(EnumType.STRING)
    private QuestionStatus status; // e.g., UNANSWERED, IN_REVIEW, ANSWERED

    private LocalDateTime askedAt = LocalDateTime.now();

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Relation to Course: The question is always about a specific course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Relation to the User who asked the question (assuming a Student entity)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // The answer provided by the TA or Professor
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer; // A separate Answer entity or just a string column.

    // Getters and Setters...


    public Question(){
        this.status = QuestionStatus.UNANSWERED;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }
}


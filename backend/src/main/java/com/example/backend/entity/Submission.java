package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    private LocalDateTime submissionDate;

    @Column(columnDefinition = "TEXT")
    private String submissionContent;

    @Column(name = "submission_file", length = 500)
    private String submissionFile;

    @Column(length = 50)
    private String status; // "submitted", "graded", "pending"

    @Column(length = 50)
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    public Submission() {}

    public Submission(Student student, Assignment assignment, LocalDateTime submissionDate,
                      String submissionContent, String status) {
        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.submissionContent = submissionContent;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
    public String getSubmissionContent() { return submissionContent; }
    public void setSubmissionContent(String submissionContent) { this.submissionContent = submissionContent; }
    public String getSubmissionFile() { return submissionFile; }
    public void setSubmissionFile(String submissionFile) { this.submissionFile = submissionFile; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}

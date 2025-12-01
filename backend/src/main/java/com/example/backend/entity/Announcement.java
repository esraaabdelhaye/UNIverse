package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relation with authors
    // These fields may be null
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctorAuthor;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taAuthor;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisorAuthor;

    @ManyToOne
    @JoinColumn(name = "studentrep_id")
    private StudentRepresentative studentRepAuthor;

    public Announcement(Long id, String title, LocalDateTime updatedAt, LocalDateTime createdAt, String content, Doctor doctorAuthor,
                        TeachingAssistant taAuthor, Supervisor supervisorAuthor, StudentRepresentative studentRepAuthor) {
        this.id = id;
        this.title = title;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.content = content;
        this.doctorAuthor = doctorAuthor;
        this.taAuthor = taAuthor;
        this.supervisorAuthor = supervisorAuthor;
        this.studentRepAuthor = studentRepAuthor;
    }

    public Announcement() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Doctor getDoctorAuthor() {
        return doctorAuthor;
    }

    public void setDoctorAuthor(Doctor doctorAuthor) {
        this.doctorAuthor = doctorAuthor;
    }

    public TeachingAssistant getTaAuthor() {
        return taAuthor;
    }

    public void setTaAuthor(TeachingAssistant taAuthor) {
        this.taAuthor = taAuthor;
    }

    public Supervisor getSupervisorAuthor() {
        return supervisorAuthor;
    }

    public void setSupervisorAuthor(Supervisor supervisorAuthor) {
        this.supervisorAuthor = supervisorAuthor;
    }

    public StudentRepresentative getStudentRepAuthor() {
        return studentRepAuthor;
    }

    public void setStudentRepAuthor(StudentRepresentative studentRepAuthor) {
        this.studentRepAuthor = studentRepAuthor;
    }
}

package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The relation between post and Docs ,TAs , Rep
 * is treated as one-sided relation for now
 * */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 50, nullable = false)
    private String status; // e.g., public, private, draft, archived


    // With the current way of manging relations there could be empty sets
    // this allows for different combination of post creating but come at
    // cost of empty sets
    @ManyToMany(mappedBy = "posts")
    private Set<Doctor> doctors = new HashSet<>();

    @ManyToMany(mappedBy = "posts")
    private Set<TeachingAssistant> tas = new HashSet<>();

    @ManyToMany(mappedBy = "posts")
    private Set<StudentRepresentative> studentReps = new HashSet<>();

    public Post() {
    }

    public Post(Long id, String title, String content, LocalDateTime createdAt,
                LocalDateTime updatedAt, String status)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<TeachingAssistant> getTas() {
        return tas;
    }

    public void setTas(Set<TeachingAssistant> tas) {
        this.tas = tas;
    }

    public Set<StudentRepresentative> getStudentReps() {
        return studentReps;
    }

    public void setStudentReps(Set<StudentRepresentative> studentReps) {
        this.studentReps = studentReps;
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
    }

    public void addTeachingAssistant(TeachingAssistant teachingAssistant) {
        this.tas.add(teachingAssistant);
    }

    public void addStudentRep(StudentRepresentative studentRepresentative) {
        this.studentReps.add(studentRepresentative);
    }

    //Add helper methods if needed
}

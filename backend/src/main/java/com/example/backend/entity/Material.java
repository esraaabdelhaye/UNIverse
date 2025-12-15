package com.example.backend.entity;

import java.time.LocalDateTime;

import com.example.backend.Utils.MaterialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Material {
    public Material() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 1024)
    private String url;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // The database saves the name of the enum
    // Instead of the ordinal number generally
    // safer for extension
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType type;

    // Relation with course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;


    // The same way we used earlier
    // For consistency across the DataBase
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Doctor doctorUploader;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taUploader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Doctor getDoctorUploader() {
        return doctorUploader;
    }

    public void setDoctorUploader(Doctor doctorUploader) {
        this.doctorUploader = doctorUploader;
    }

    public TeachingAssistant getTaUploader() {
        return taUploader;
    }

    public void setTaUploader(TeachingAssistant taUploader) {
        this.taUploader = taUploader;
    }
}

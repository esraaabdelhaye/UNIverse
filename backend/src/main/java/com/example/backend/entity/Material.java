package com.example.backend.entity;

import com.example.backend.Utils.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Material {
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

}

package com.example.backend.entity;

import com.example.backend.Utils.MaterialType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    private String fileName;

    @Column(name = "url")
    private String url;

    @Column(name = "file_size")
    private Long fileSize; // in bytes

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MaterialType type;


    @Column(length = 1000)
    private String description;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "icon_name")
    private String iconName; // e.g., "picture_as_pdf", "play_circle", "mic", "menu_book", "description"

    @Column(name = "icon_color")
    private String iconColor; // e.g., "primary-icon", "red-icon", "amber-icon", "green-icon"

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctorUploader;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taUploader;

    // Getters and Setters
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
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


    public String getFormattedFileSize() {
        if (fileSize == null || fileSize == 0) {
            return "Unknown";
        }
        long bytes = fileSize;
        int unit = 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
package com.example.backend.dto;

import java.time.LocalDateTime;

public class AnnouncementDTO {
    private String announcementId;
    private String courseCode;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdDate;
    private String status;
    private String visibility;

    public AnnouncementDTO() {}

    public AnnouncementDTO(String announcementId, String courseCode, String title, String content,
                           String createdBy, LocalDateTime createdDate, String status, String visibility) {
        this.announcementId = announcementId;
        this.courseCode = courseCode;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.status = status;
        this.visibility = visibility;
    }

    public String getAnnouncementId() { return announcementId; }
    public void setAnnouncementId(String announcementId) { this.announcementId = announcementId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
}


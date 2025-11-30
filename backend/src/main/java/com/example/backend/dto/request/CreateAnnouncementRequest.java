package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class CreateAnnouncementRequest {
    private String courseCode;
    private String title;
    private String content;
    private LocalDateTime publishDate;
    private String visibility;
    private String[] attachments;

    public CreateAnnouncementRequest() {}

    public CreateAnnouncementRequest(String courseCode, String title, String content,
                                     LocalDateTime publishDate, String visibility, String[] attachments) {
        this.courseCode = courseCode;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.visibility = visibility;
        this.attachments = attachments;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }
}
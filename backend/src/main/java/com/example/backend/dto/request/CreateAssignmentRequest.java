package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class CreateAssignmentRequest {
    private String courseCode;
    private String assignmentTitle;
    private String description;
    private LocalDateTime dueDate;
    private String[] attachments;

    public CreateAssignmentRequest() {}

    public CreateAssignmentRequest(String courseCode, String assignmentTitle, String description,
                                   LocalDateTime dueDate, String[] attachments) {
        this.courseCode = courseCode;
        this.assignmentTitle = assignmentTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.attachments = attachments;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }
}

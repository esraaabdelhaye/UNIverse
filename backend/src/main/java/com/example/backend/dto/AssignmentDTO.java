package com.example.backend.dto;

import java.time.LocalDateTime;

public class AssignmentDTO {
    private String assignmentId;
    private String courseCode;
    private String assignmentTitle;
    private LocalDateTime dueDate;
    private String status;
    private Double grade;
    private String feedbackUrl;

    public AssignmentDTO() {}

    public AssignmentDTO(String assignmentId, String courseCode, String assignmentTitle,
                         LocalDateTime dueDate, String status, Double grade, String feedbackUrl) {
        this.assignmentId = assignmentId;
        this.courseCode = courseCode;
        this.assignmentTitle = assignmentTitle;
        this.dueDate = dueDate;
        this.status = status;
        this.grade = grade;
        this.feedbackUrl = feedbackUrl;
    }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getFeedbackUrl() { return feedbackUrl; }
    public void setFeedbackUrl(String feedbackUrl) { this.feedbackUrl = feedbackUrl; }
}

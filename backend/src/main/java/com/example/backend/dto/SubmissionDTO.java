package com.example.backend.dto;

import java.time.LocalDateTime;

public class SubmissionDTO {
    private String submissionId;
    private String studentId;
    private String studentName;
    private String assignmentTitle;
    private LocalDateTime submissionDate;
    private String status;
    private Double grade;

    public SubmissionDTO() {}

    public SubmissionDTO(String submissionId, String studentId, String studentName, String assignmentTitle,
                         LocalDateTime submissionDate, String status, Double grade) {
        this.submissionId = submissionId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.assignmentTitle = assignmentTitle;
        this.submissionDate = submissionDate;
        this.status = status;
        this.grade = grade;
    }

    public String getSubmissionId() { return submissionId; }
    public void setSubmissionId(String submissionId) { this.submissionId = submissionId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }
}

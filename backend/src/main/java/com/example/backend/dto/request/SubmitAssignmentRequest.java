package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class SubmitAssignmentRequest {
    private String assignmentId;
    private String studentId;
    private String submissionContent;
    private byte[] submissionFile;
    private LocalDateTime submissionDate;

    public SubmitAssignmentRequest() {}

    public SubmitAssignmentRequest(String assignmentId, String studentId, String submissionContent,
                                   byte[] submissionFile, LocalDateTime submissionDate) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submissionContent = submissionContent;
        this.submissionFile = submissionFile;
        this.submissionDate = submissionDate;
    }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSubmissionContent() { return submissionContent; }
    public void setSubmissionContent(String submissionContent) { this.submissionContent = submissionContent; }

    public byte[] getSubmissionFile() { return submissionFile; }
    public void setSubmissionFile(byte[] submissionFile) { this.submissionFile = submissionFile; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
}
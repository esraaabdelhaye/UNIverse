package com.example.backend.dto.request;

public class GradeAssignmentRequest {
    private String studentId;
    private String assignmentId;
    private Double grade;
    private String feedback;

    public GradeAssignmentRequest() {}

    public GradeAssignmentRequest(String studentId, String assignmentId, Double grade, String feedback) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.grade = grade;
        this.feedback = feedback;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}

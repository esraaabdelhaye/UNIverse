package com.example.backend.dto;

import java.time.LocalDateTime;

public class GradeDTO {
    private String gradeId;
    private String studentId;
    private String studentName;
    private String courseCode;
    private String courseTitle;
    private Double score;
    private String letterGrade;
    private String gradingStatus;
    private String feedback;
    private LocalDateTime gradedDate;
    private String gradedBy;

    public GradeDTO() {}

    public GradeDTO(String gradeId, String studentId, String studentName, String courseCode,
                    String courseTitle, Double score, String letterGrade, String gradingStatus,
                    String feedback, LocalDateTime gradedDate, String gradedBy) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.score = score;
        this.letterGrade = letterGrade;
        this.gradingStatus = gradingStatus;
        this.feedback = feedback;
        this.gradedDate = gradedDate;
        this.gradedBy = gradedBy;
    }

    public String getGradeId() { return gradeId; }
    public void setGradeId(String gradeId) { this.gradeId = gradeId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getLetterGrade() { return letterGrade; }
    public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }

    public String getGradingStatus() { return gradingStatus; }
    public void setGradingStatus(String gradingStatus) { this.gradingStatus = gradingStatus; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public LocalDateTime getGradedDate() { return gradedDate; }
    public void setGradedDate(LocalDateTime gradedDate) { this.gradedDate = gradedDate; }

    public String getGradedBy() { return gradedBy; }
    public void setGradedBy(String gradedBy) { this.gradedBy = gradedBy; }
}


package com.example.backend.dto;

import java.time.LocalDateTime;

public class CourseDTO {
    private Long id;
    private String courseCode;
    private String courseTitle;
    private String department;
    private String instructorId;
    private String instructorName;
    private String description;
    private Integer capacity;
    private Integer enrolled;
    private Integer credits;
    private String semester;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CourseDTO() {}

    public CourseDTO(String courseCode, String courseTitle, String department, String instructorId,
                     String instructorName, String description, Integer capacity, Integer enrolled,
                     Integer credits, String semester, String status, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.department = department;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = enrolled;
        this.credits = credits;
        this.semester = semester;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getEnrolled() { return enrolled; }
    public void setEnrolled(Integer enrolled) { this.enrolled = enrolled; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
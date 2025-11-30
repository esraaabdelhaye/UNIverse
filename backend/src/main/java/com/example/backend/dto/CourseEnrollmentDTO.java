package com.example.backend.dto;

public class CourseEnrollmentDTO {
    private String courseCode;
    private String courseTitle;
    private String professorName;
    private String department;
    private Integer enrolledStudents;
    private Integer totalCapacity;
    private String status;

    public CourseEnrollmentDTO() {}

    public CourseEnrollmentDTO(String courseCode, String courseTitle, String professorName,
                               String department, Integer enrolledStudents, Integer totalCapacity, String status) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.professorName = professorName;
        this.department = department;
        this.enrolledStudents = enrolledStudents;
        this.totalCapacity = totalCapacity;
        this.status = status;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(Integer enrolledStudents) { this.enrolledStudents = enrolledStudents; }

    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

package com.example.backend.dto;

import java.time.LocalDate;

public class SemesterDTO {
    private String semesterId;
    private String semesterName;
    private String semesterCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Integer totalCourses;
    private Integer totalEnrolledStudents;
    private String academicYear;

    public SemesterDTO() {}

    public SemesterDTO(String semesterId, String semesterName, String semesterCode,
                       LocalDate startDate, LocalDate endDate, Boolean isActive,
                       Integer totalCourses, Integer totalEnrolledStudents, String academicYear) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.semesterCode = semesterCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.totalCourses = totalCourses;
        this.totalEnrolledStudents = totalEnrolledStudents;
        this.academicYear = academicYear;
    }

    public String getSemesterId() { return semesterId; }
    public void setSemesterId(String semesterId) { this.semesterId = semesterId; }

    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }

    public String getSemesterCode() { return semesterCode; }
    public void setSemesterCode(String semesterCode) { this.semesterCode = semesterCode; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getTotalCourses() { return totalCourses; }
    public void setTotalCourses(Integer totalCourses) { this.totalCourses = totalCourses; }

    public Integer getTotalEnrolledStudents() { return totalEnrolledStudents; }
    public void setTotalEnrolledStudents(Integer totalEnrolledStudents) { this.totalEnrolledStudents = totalEnrolledStudents; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
}

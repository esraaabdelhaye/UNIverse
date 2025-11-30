package com.example.backend.dto;

import java.time.LocalDate;

public class StudentDTO {
    private String studentId;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String enrollmentStatus;

    public StudentDTO() {}

    public StudentDTO(String studentId, String fullName, String email, LocalDate dateOfBirth,
                      String phoneNumber, String enrollmentStatus) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }
}


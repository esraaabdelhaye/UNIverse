package com.example.backend.dto.request;

import java.time.LocalDate;

public class RegisterStudentRequest {
    private String fullName;
    private String studentEmail;
    private String studentId;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    public RegisterStudentRequest() {}

    public RegisterStudentRequest(String fullName, String studentEmail, String studentId,
                                  LocalDate dateOfBirth, String phoneNumber) {
        this.fullName = fullName;
        this.studentEmail = studentEmail;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

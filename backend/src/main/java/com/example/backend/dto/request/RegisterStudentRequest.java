package com.example.backend.dto.request;

import java.time.LocalDate;

public class RegisterStudentRequest {
    private String fullName;
    private String email;
    private String studentId;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String password ;

    public RegisterStudentRequest() {}

    public RegisterStudentRequest(String fullName, String email, String studentId,
                                  LocalDate dateOfBirth, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

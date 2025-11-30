package com.example.backend.dto.request;

public class RegisterTARequest {
    private String fullName;
    private String facultyEmail;
    private String facultyEmployeeId;
    private String phoneNumber;
    private String role;

    public RegisterTARequest() {}

    public RegisterTARequest(String fullName, String facultyEmail, String facultyEmployeeId,
                                        String phoneNumber, String role) {
        this.fullName = fullName;
        this.facultyEmail = facultyEmail;
        this.facultyEmployeeId = facultyEmployeeId;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getFacultyEmail() { return facultyEmail; }
    public void setFacultyEmail(String facultyEmail) { this.facultyEmail = facultyEmail; }

    public String getFacultyEmployeeId() { return facultyEmployeeId; }
    public void setFacultyEmployeeId(String facultyEmployeeId) { this.facultyEmployeeId = facultyEmployeeId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

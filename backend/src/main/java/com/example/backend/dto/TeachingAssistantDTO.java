package com.example.backend.dto;

public class TeachingAssistantDTO {
    private String employeeId;
    private String fullName;
    private String email;
    private String role;
    private String phoneNumber;
    private String department;

    public TeachingAssistantDTO() {}

    public TeachingAssistantDTO(String employeeId, String fullName, String email, String role,
                            String phoneNumber, String department) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}

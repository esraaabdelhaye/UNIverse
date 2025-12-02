package com.example.backend.dto.request;

public class SignupRequest {
    private String email;
    private String password;
    private String role;
    private String fullName;
    private Long academicId;
    private Long departmentId;

    public SignupRequest() {}

    public SignupRequest(String email, String password, String role, String fullName, Long academicId, Long departmentId) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.academicId = academicId;
        this.departmentId = departmentId;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Long getAcademicId() { return academicId; }
    public void setAcademicId(Long academicId) { this.academicId = academicId; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}

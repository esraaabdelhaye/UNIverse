package com.example.backend.dto.request;

public class RegisterStudentRepRequest {
    private String fullName;
    private String email;
    private String studentId;
    private String phoneNumber;
    private String department;
    private String section;
    private String semester;

    public RegisterStudentRepRequest() {}

    public RegisterStudentRepRequest(String fullName, String email, String studentId, String phoneNumber,
                                     String department, String section, String semester) {
        this.fullName = fullName;
        this.email = email;
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.section = section;
        this.semester = semester;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}

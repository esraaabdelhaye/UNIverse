package com.example.backend.dto;

import java.time.LocalDateTime;

public class DepartmentDTO {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String headName;
    private String building;
    private String phoneNumber;
    private String email;
    private Integer totalFaculty;
    private Integer totalStudents;
    private LocalDateTime createdDate;

    public DepartmentDTO() {}

    public DepartmentDTO(String departmentId, String departmentName, String departmentCode,
                         String headName, String building, String phoneNumber, String email,
                         Integer totalFaculty, Integer totalStudents, LocalDateTime createdDate) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.headName = headName;
        this.building = building;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.totalFaculty = totalFaculty;
        this.totalStudents = totalStudents;
        this.createdDate = createdDate;
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getHeadName() { return headName; }
    public void setHeadName(String headName) { this.headName = headName; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(Integer totalFaculty) { this.totalFaculty = totalFaculty; }

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}


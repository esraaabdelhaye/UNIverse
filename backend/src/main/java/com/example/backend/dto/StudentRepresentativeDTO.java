package com.example.backend.dto;

import java.time.LocalDate;

public class StudentRepresentativeDTO {
    private String representativeId;
    private String studentId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String department;
    private String section;
    private String semester;
    private LocalDate appointmentDate;
    private LocalDate endDate;
    private String status;
    private Integer constituentsCount;

    public StudentRepresentativeDTO() {}

    public StudentRepresentativeDTO(String representativeId, String studentId, String fullName, String email,
                                    String phoneNumber, String department, String section, String semester,
                                    LocalDate appointmentDate, LocalDate endDate, String status, Integer constituentsCount) {
        this.representativeId = representativeId;
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.section = section;
        this.semester = semester;
        this.appointmentDate = appointmentDate;
        this.endDate = endDate;
        this.status = status;
        this.constituentsCount = constituentsCount;
    }

    public String getRepresentativeId() { return representativeId; }
    public void setRepresentativeId(String representativeId) { this.representativeId = representativeId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getConstituentsCount() { return constituentsCount; }
    public void setConstituentsCount(Integer constituentsCount) { this.constituentsCount = constituentsCount; }
}
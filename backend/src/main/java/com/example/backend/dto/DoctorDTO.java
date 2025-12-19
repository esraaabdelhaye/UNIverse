package com.example.backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO implements  PostAuthor, AnnouncementAuthor, PollAuthor, EventAuthor {
    private String doctorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String department;
    private String officeLocation;
    private String qualifications;
    private Boolean availableForConsultation;
    private LocalDate startDate;
    private String role;
    private String password; // For registration only
    private int courseCount;
    private boolean active;



    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public boolean isAvailableForConsultation() { // Getter name changed from getAvailableForConsultation to isAvailableForConsultation
        return availableForConsultation;
    }

    public void setAvailableForConsultation(boolean availableForConsultation) {
        this.availableForConsultation = availableForConsultation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

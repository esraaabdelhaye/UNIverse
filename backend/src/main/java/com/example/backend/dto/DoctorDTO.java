package com.example.backend.dto;

import java.time.LocalDate;

public class DoctorDTO {
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

    public DoctorDTO() {}

    public DoctorDTO(String doctorId, String fullName, String email, String phoneNumber,
                     String specialization, String department, String officeLocation,
                     String qualifications, Boolean availableForConsultation, LocalDate startDate) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.department = department;
        this.officeLocation = officeLocation;
        this.qualifications = qualifications;
        this.availableForConsultation = availableForConsultation;
        this.startDate = startDate;
    }

    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public Boolean getAvailableForConsultation() { return availableForConsultation; }
    public void setAvailableForConsultation(Boolean availableForConsultation) { this.availableForConsultation = availableForConsultation; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
}

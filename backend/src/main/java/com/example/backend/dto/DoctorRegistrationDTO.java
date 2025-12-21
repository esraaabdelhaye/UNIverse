package com.example.backend.dto;

public class DoctorRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String officeLocation;
    private String title;
    private String expertise;
    private Long departmentId;

    // Constructors
    public DoctorRegistrationDTO() {}

    public DoctorRegistrationDTO(String name, String email, String password, String phoneNumber,
                                  String officeLocation, String title, String expertise, Long departmentId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.title = title;
        this.expertise = expertise;
        this.departmentId = departmentId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}

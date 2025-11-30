package com.example.backend.dto.request;

public class RegisterDoctorRequest {
    private String fullName;
    private String email;
    private String employeeId;
    private String phoneNumber;
    private String specialization;
    private String department;
    private String officeLocation;

    public RegisterDoctorRequest() {}

    public RegisterDoctorRequest(String fullName, String email, String employeeId, String phoneNumber,
                                 String specialization, String department, String officeLocation) {
        this.fullName = fullName;
        this.email = email;
        this.employeeId = employeeId;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.department = department;
        this.officeLocation = officeLocation;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }
}

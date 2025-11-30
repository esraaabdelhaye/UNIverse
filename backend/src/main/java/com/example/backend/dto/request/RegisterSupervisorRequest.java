package com.example.backend.dto.request;

public class RegisterSupervisorRequest {
    private String fullName;
    private String email;
    private String employeeId;
    private String department;
    private String positionTitle;
    private String officeLocation;

    public RegisterSupervisorRequest() {}

    public RegisterSupervisorRequest(String fullName, String email, String employeeId,
                                     String department, String positionTitle, String officeLocation) {
        this.fullName = fullName;
        this.email = email;
        this.employeeId = employeeId;
        this.department = department;
        this.positionTitle = positionTitle;
        this.officeLocation = officeLocation;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPositionTitle() { return positionTitle; }
    public void setPositionTitle(String positionTitle) { this.positionTitle = positionTitle; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }
}

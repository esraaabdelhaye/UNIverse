package com.example.backend.dto.request;

public class CreateCourseRequest {
    private String courseCode;
    private String courseTitle;
    private String department;
    private String instructorId;
    private Integer capacity;
    private String semester;

    public CreateCourseRequest() {}

    public CreateCourseRequest(String courseCode, String courseTitle, String department,
                               String instructorId, Integer capacity, String semester) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.department = department;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.semester = semester;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}

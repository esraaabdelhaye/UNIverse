package com.example.backend.dto;

import java.time.LocalDateTime;

public class ScheduleDTO {
    private String eventId;
    private String eventTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String courseCode;
    private String eventType;
    private String academicYear;

    public ScheduleDTO() {}

    public ScheduleDTO(String eventId, String eventTitle, LocalDateTime startTime,
                       LocalDateTime endTime, String location, String courseCode, String eventType, String academicYear) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.courseCode = courseCode;
        this.eventType = eventType;
        this.academicYear = academicYear;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
}

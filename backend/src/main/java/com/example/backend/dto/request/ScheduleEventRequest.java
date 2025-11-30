package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class ScheduleEventRequest {
    private String courseCode;
    private String eventTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String eventType;

    public ScheduleEventRequest() {}

    public ScheduleEventRequest(String courseCode, String eventTitle, LocalDateTime startTime,
                                LocalDateTime endTime, String location, String eventType) {
        this.courseCode = courseCode;
        this.eventTitle = eventTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.eventType = eventType;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
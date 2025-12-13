package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class CreateEventRequest {
    private String eventTitle;
    private String eventDescription;
    private LocalDateTime eventDateTime;
    private LocalDateTime eventEndDateTime;
    private String location;
    private String eventType;
    private String courseCode;

    public CreateEventRequest() {}

    public CreateEventRequest(String eventTitle, String eventDescription, LocalDateTime eventDateTime,
                              String location, String eventType, String courseCode) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.location = location;
        this.eventType = eventType;
        this.courseCode = courseCode;
    }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getEventDescription() { return eventDescription; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public LocalDateTime getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(LocalDateTime eventDateTime) { this.eventDateTime = eventDateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public LocalDateTime getEventEndDateTime() {
        return eventEndDateTime;
    }

    public void setEventEndDateTime(LocalDateTime eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }
}

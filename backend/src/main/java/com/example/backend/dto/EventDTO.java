package com.example.backend.dto;

import java.time.LocalDateTime;

public class EventDTO {
    private String eventId;
    private String eventTitle;
    private String eventDescription;
    private LocalDateTime eventDateTime;
    private LocalDateTime eventEndDateTime;
    private String location;
    private String eventType;
    private Integer attendeeCount;

    public EventDTO() {}

    public EventDTO(String eventId, String eventTitle, String eventDescription, LocalDateTime eventDateTime,
                    String location, String eventType, Integer attendeeCount) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.location = location;
        this.eventType = eventType;
        this.attendeeCount = attendeeCount;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

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

    public Integer getAttendeeCount() { return attendeeCount; }
    public void setAttendeeCount(Integer attendeeCount) { this.attendeeCount = attendeeCount; }

    public LocalDateTime getEventEndDateTime() {
        return eventEndDateTime;
    }

    public void setEventEndDateTime(LocalDateTime eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }
}

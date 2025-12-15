package com.example.backend.dto.request;

import java.util.List;
import java.util.Map;

public class TimetableRequest {
    private List<Long> courseIds;
    private List<String> rooms;
    private List<String> days; 
    private List<String> timeSlots; // "09:00", "10:00"
    private String semester;
    private String academicYear;
    private Map<Long, String> fixedRooms; // CourseID -> RoomName constraint

    public TimetableRequest() {}

    public List<Long> getCourseIds() { return courseIds; }
    public void setCourseIds(List<Long> courseIds) { this.courseIds = courseIds; }

    public List<String> getRooms() { return rooms; }
    public void setRooms(List<String> rooms) { this.rooms = rooms; }

    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }

    public List<String> getTimeSlots() { return timeSlots; }
    public void setTimeSlots(List<String> timeSlots) { this.timeSlots = timeSlots; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public Map<Long, String> getFixedRooms() { return fixedRooms; }
    public void setFixedRooms(Map<Long, String> fixedRooms) { this.fixedRooms = fixedRooms; }
}

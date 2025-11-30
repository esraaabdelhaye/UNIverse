package com.example.backend.dto.request;

public class GenerateTimetableRequest {
    private String semester;
    private String[] coursesToInclude;
    private String[] instructorAvailability;
    private String[] roomCapacityPreferences;
    private Boolean avoidBackToBackClasses;
    private Boolean prioritizeMorningSlots;

    public GenerateTimetableRequest() {}

    public GenerateTimetableRequest(String semester, String[] coursesToInclude, String[] instructorAvailability,
                                    String[] roomCapacityPreferences, Boolean avoidBackToBackClasses, Boolean prioritizeMorningSlots) {
        this.semester = semester;
        this.coursesToInclude = coursesToInclude;
        this.instructorAvailability = instructorAvailability;
        this.roomCapacityPreferences = roomCapacityPreferences;
        this.avoidBackToBackClasses = avoidBackToBackClasses;
        this.prioritizeMorningSlots = prioritizeMorningSlots;
    }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String[] getCoursesToInclude() { return coursesToInclude; }
    public void setCoursesToInclude(String[] coursesToInclude) { this.coursesToInclude = coursesToInclude; }

    public String[] getInstructorAvailability() { return instructorAvailability; }
    public void setInstructorAvailability(String[] instructorAvailability) { this.instructorAvailability = instructorAvailability; }

    public String[] getRoomCapacityPreferences() { return roomCapacityPreferences; }
    public void setRoomCapacityPreferences(String[] roomCapacityPreferences) { this.roomCapacityPreferences = roomCapacityPreferences; }

    public Boolean getAvoidBackToBackClasses() { return avoidBackToBackClasses; }
    public void setAvoidBackToBackClasses(Boolean avoidBackToBackClasses) { this.avoidBackToBackClasses = avoidBackToBackClasses; }

    public Boolean getPrioritizeMorningSlots() { return prioritizeMorningSlots; }
    public void setPrioritizeMorningSlots(Boolean prioritizeMorningSlots) { this.prioritizeMorningSlots = prioritizeMorningSlots; }
}

package com.example.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.TimetableRequest;
import com.example.backend.entity.Course;
import com.example.backend.entity.Schedule;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.ScheduleRepo;

import jakarta.transaction.Transactional;

@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final CourseRepo courseRepo;

    public ScheduleService(ScheduleRepo scheduleRepo, CourseRepo courseRepo) {
        this.scheduleRepo = scheduleRepo;
        this.courseRepo = courseRepo;
    }

    public List<Schedule> getSchedule(String semester, String academicYear) {
        if (academicYear != null && !academicYear.isEmpty()) {
            return scheduleRepo.findBySemesterAndAcademicYear(semester, academicYear);
        }
        return scheduleRepo.findBySemester(semester);
    }

    @Transactional
    public List<Schedule> generateTimetable(TimetableRequest request) {
        // 1. Clear existing schedule for the semester and academic year
        String academicYear = request.getAcademicYear();
        scheduleRepo.deleteBySemesterAndAcademicYear(request.getSemester(), academicYear);

        // 2. Fetch all courses
        List<Course> courses = courseRepo.findAllById(request.getCourseIds());
        System.out.println("[TIMETABLE GEN] Requested course IDs: " + request.getCourseIds());
        System.out.println("[TIMETABLE GEN] Found " + courses.size() + " courses in DB");
        for (Course c : courses) {
            System.out.println("  - Course: " + c.getCourseCode() + " (ID: " + c.getId() + "), Doctors: " + c.getDoctors().size());
        }

        // 3. SMART DAY DISTRIBUTION
        // Calculate required days based on course count
        int requiredDays = calculateRequiredDays(courses.size());
        System.out.println("[SMART SCHEDULING] Course count: " + courses.size() + " → Required days: " + requiredDays);
        
        // Randomly select days from available days
        List<String> selectedDays = selectRandomDays(requiredDays, request.getDays());
        System.out.println("[SMART SCHEDULING] Selected days (random): " + selectedDays);
        
        // Limit time slots to 5 per day (max capacity)
        List<String> limitedTimeSlots = request.getTimeSlots().subList(0, Math.min(5, request.getTimeSlots().size()));
        System.out.println("[SMART SCHEDULING] Time slots limited to " + limitedTimeSlots.size() + ": " + limitedTimeSlots);
        
        // 4. Setup CSP Variables and Domains
        List<CSPVariable> variables = new ArrayList<>();
        List<TimeSlot> allSlots = new ArrayList<>();

        // Create all possible slots (Day + Time + Room) using SELECTED days and LIMITED time slots
        for (String day : selectedDays) {
            for (String time : limitedTimeSlots) {
                for (String room : request.getRooms()) {
                    allSlots.add(new TimeSlot(day, time, room));
                }
            }
        }
        System.out.println("[TIMETABLE GEN] Created " + allSlots.size() + " total time slots");
        System.out.println("  Selected Days: " + selectedDays);
        System.out.println("  Limited Times: " + limitedTimeSlots);
        System.out.println("  Rooms: " + request.getRooms());

        // ROUND-ROBIN DAY ASSIGNMENT for even distribution
        System.out.println("[ROUND-ROBIN] Assigning courses to days evenly...");
        int courseIndex = 0;
        for (Course course : courses) {
            // Assign this course to a specific day using round-robin
            int dayIndex = courseIndex % selectedDays.size();
            String assignedDay = selectedDays.get(dayIndex);
            
            System.out.println("  - Course " + course.getCourseCode() + " → " + assignedDay + " (round-robin " + (courseIndex + 1) + ")");
            
            // Create domain with ONLY the assigned day (forcing round-robin distribution)
            List<TimeSlot> variableDomain = new ArrayList<>();
            for (String time : limitedTimeSlots) {
                for (String room : request.getRooms()) {
                    variableDomain.add(new TimeSlot(assignedDay, time, room));
                }
            }
            
            // Apply Room Constraint if present
            if (request.getFixedRooms() != null && request.getFixedRooms().containsKey(course.getId())) {
                String fixedRoom = request.getFixedRooms().get(course.getId());
                variableDomain.removeIf(slot -> !slot.room.equals(fixedRoom));
                System.out.println("    → Fixed to room " + fixedRoom + ", domain size: " + variableDomain.size());
            } else {
                System.out.println("    → Domain size: " + variableDomain.size());
            }

            variables.add(new CSPVariable(course, variableDomain));
            courseIndex++;
        }

        // 5. Load existing schedules from OTHER years to check for global conflicts
        // We need to check against ALL schedules for the same semester, excluding the current academic year we are generating
        List<Schedule> existingSchedules = scheduleRepo.findBySemester(request.getSemester());
        System.out.println("[TIMETABLE GEN] Found " + existingSchedules.size() + " existing schedules for semester " + request.getSemester());
        // Remove ones we just deleted (though delete happens on DB, this list might be stale if fetched before, but here we fetch after delete? No, flush likely needed or just filter)
        // Actually, since we called deleteBySemesterAndAcademicYear, findBySemester will return remaining years.
        
        // 5. Backtracking Search
        Map<Long, TimeSlot> assignment = new HashMap<>();
        System.out.println("[TIMETABLE GEN] Starting backtracking search for " + variables.size() + " courses...");
        if (backtrackingSearch(variables, assignment, existingSchedules)) {
            System.out.println("[TIMETABLE GEN] SUCCESS! Generated schedule for " + assignment.size() + " courses");
            // Save Schedule
            List<Schedule> generatedSchedule = new ArrayList<>();
            for (Map.Entry<Long, TimeSlot> entry : assignment.entrySet()) {
                Course c = courses.stream().filter(x -> x.getId().equals(entry.getKey())).findFirst().orElseThrow();
                TimeSlot slot = entry.getValue();
                
                String endTime = calculateEndTime(slot.time); 

                Schedule s = new Schedule(c, slot.room, slot.day, slot.time, endTime, request.getSemester(), academicYear);
                generatedSchedule.add(s);
                System.out.println("  - Scheduled: " + c.getCourseCode() + " on " + slot.day + " at " + slot.time + " in " + slot.room);
            }
            return scheduleRepo.saveAll(generatedSchedule);
        } else {
            System.out.println("[TIMETABLE GEN] FAILED! Could only assign " + assignment.size() + " out of " + variables.size() + " courses");
            throw new RuntimeException("Could not generate a valid timetable with given constraints.");
        }
    }

    // Manual CRUD Operations
    public Schedule addManualSlot(Schedule s) {
        // Ensure Course is managed
        if (s.getCourse() != null && s.getCourse().getId() != null) {
            Course c = courseRepo.findById(s.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Course with ID " + s.getCourse().getId() + " not found"));
            s.setCourse(c);
        } else {
             throw new IllegalArgumentException("Course ID is required");
        }

        // Validate conflicts
        if (hasConflict(s)) {
            throw new IllegalArgumentException("Conflict detected: This slot conflicts with an existing schedule.");
        }
        return scheduleRepo.save(s);
    }

    public Schedule updateManualSlot(Long id, Schedule updatedSchedule) {
        Schedule existing = scheduleRepo.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        
        // Temporarily delete or ignore existing slot for conflict check? 
        // Better: Check conflict assuming existing is removed
        // We'll trust the caller passes a fully formed Schedule object or we merge properties.
        // For simplicity, let's assume we are replacing everything except ID.
        updatedSchedule.setSemester(existing.getSemester()); // Ensure consistency if needed
        
        // Check conflicts excluding the current slot
        if (hasConflictExcluding(updatedSchedule, id)) {
             throw new IllegalArgumentException("Conflict detected: This slot conflicts with an existing schedule.");
        }
        
        if (updatedSchedule.getCourse() != null && updatedSchedule.getCourse().getId() != null) {
             Course c = courseRepo.findById(updatedSchedule.getCourse().getId())
                 .orElseThrow(() -> new RuntimeException("Course not found"));
             existing.setCourse(c);
        } else if (updatedSchedule.getCourse() != null) {
            // Case where full course might be passed or just ignored if null?
            // If explicit null, we might not want to clear it unless intended.
            // For now, assume ID is required if course is changing.
        }
        
        existing.setDayOfWeek(updatedSchedule.getDayOfWeek());
        existing.setRoom(updatedSchedule.getRoom());
        existing.setStartTime(updatedSchedule.getStartTime());
        existing.setEndTime(updatedSchedule.getEndTime());
        existing.setAcademicYear(updatedSchedule.getAcademicYear());
        
        return scheduleRepo.save(existing);
    }

    public void deleteSlot(Long id) {
        scheduleRepo.deleteById(id);
    }

    private boolean backtrackingSearch(List<CSPVariable> variables, Map<Long, TimeSlot> assignment, List<Schedule> existingSchedules) {
        if (assignment.size() == variables.size()) {
            return true;
        }

        CSPVariable var = variables.stream()
                .filter(v -> !assignment.containsKey(v.course.getId()))
                .findFirst()
                .orElse(null);

        if (var == null) return true;

        for (TimeSlot value : var.domain) {
            if (isConsistent(var, value, assignment, variables, existingSchedules)) {
                assignment.put(var.course.getId(), value);
                if (backtrackingSearch(variables, assignment, existingSchedules)) {
                    return true;
                }
                assignment.remove(var.course.getId());
            }
        }
        return false;
    }

    private boolean isConsistent(CSPVariable var, TimeSlot value, Map<Long, TimeSlot> assignment, List<CSPVariable> allVars, List<Schedule> existingSchedules) {
        // 1. Check against currently building assignment (Internal Consistency)
        // IMPORTANT: All courses in 'allVars' are for the SAME academic year (same request)
        // So they CANNOT share the same day+time (students can't attend two classes at once)
        for (Map.Entry<Long, TimeSlot> entry : assignment.entrySet()) {
            Long assignedCourseId = entry.getKey();
            TimeSlot assignedSlot = entry.getValue();
            
            Course assignedCourse = allVars.stream()
                    .filter(v -> v.course.getId().equals(assignedCourseId))
                    .findFirst().map(v -> v.course).orElse(null);

            // Check for conflicts
            // For courses in the SAME academic year (same request), prohibit same day+time
            if (value.day.equals(assignedSlot.day) && value.time.equals(assignedSlot.time)) {
                // Same day and time - this is a conflict for same academic year
                return false;
            }
            
            // Also check traditional conflicts (room, instructor)
            if (isConflict(value, var.course, assignedSlot, assignedCourse)) {
                return false;
            }
        }

        // 2. Check against existing schedules in DB (Global Consistency across years/manual slots)
        // These are from OTHER academic years, so we only check room/instructor conflicts
        for (Schedule existing : existingSchedules) {
            // Convert existing schedule to TimeSlot format for comparison
            TimeSlot existingSlot = new TimeSlot(existing.getDayOfWeek(), existing.getStartTime(), existing.getRoom());
            // Reuse Check
            if (isConflict(value, var.course, existingSlot, existing.getCourse())) {
                return false;
            }
        }

        return true;
    }

    // New helper to perform the actual logic
    private boolean isConflict(TimeSlot slot1, Course course1, TimeSlot slot2, Course course2) {
         // Same Room, Same Time, Same Day - Room Conflict
         if (slot1.room.equals(slot2.room) && 
             slot1.day.equals(slot2.day) && 
             slot1.time.equals(slot2.time)) {
             return true;
         }

         // Instructor Conflict - Same instructor teaching two courses at the same time
         if (slot1.day.equals(slot2.day) && slot1.time.equals(slot2.time)) {
             if (course1.getDoctors() != null && course2.getDoctors() != null) {
                 // Check if any doctor is teaching both courses
                 boolean hasCommonDoctor = course1.getDoctors().stream()
                     .anyMatch(d1 -> course2.getDoctors().stream()
                         .anyMatch(d2 -> d1.getId().equals(d2.getId())));
                 if (hasCommonDoctor) {
                     return true;
                 }
             }
         }
         return false;
    }

    private boolean hasConflict(Schedule s) {
        // Check against all schedules in semester
        List<Schedule> others = scheduleRepo.findBySemester(s.getSemester());
        TimeSlot newSlot = new TimeSlot(s.getDayOfWeek(), s.getStartTime(), s.getRoom());
        
        for (Schedule other : others) {
            TimeSlot otherSlot = new TimeSlot(other.getDayOfWeek(), other.getStartTime(), other.getRoom());
            if (isConflict(newSlot, s.getCourse(), otherSlot, other.getCourse())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasConflictExcluding(Schedule s, Long excludeId) {
        List<Schedule> others = scheduleRepo.findBySemester(s.getSemester());
        TimeSlot newSlot = new TimeSlot(s.getDayOfWeek(), s.getStartTime(), s.getRoom());
        
        for (Schedule other : others) {
            if (other.getId().equals(excludeId)) continue;
            
            TimeSlot otherSlot = new TimeSlot(other.getDayOfWeek(), other.getStartTime(), other.getRoom());
            if (isConflict(newSlot, s.getCourse(), otherSlot, other.getCourse())) {
                return true;
            }
        }
        return false;
    }
    
    private String calculateEndTime(String startTime) {
        try {
            String[] parts = startTime.split(":");
            int hour = Integer.parseInt(parts[0]);
            int nextHour = hour + 1;
            return String.format("%02d:%s", nextHour, parts[1]);
        } catch (Exception e) {
            return startTime;
        }
    }

    // =============== SMART DAY DISTRIBUTION HELPERS ===============
    
    /**
     * Calculate required days based on course count for optimal distribution
     * 1-4 courses → 1 day
     * 5-8 courses → 2 days
     * 9-12 courses → 3 days
     * 13-16 courses → 4 days
     * 17+ courses → 5 days (max)
     */
    private int calculateRequiredDays(int courseCount) {
        if (courseCount <= 4) return 1;
        if (courseCount <= 8) return 2;
        if (courseCount <= 12) return 3;
        if (courseCount <= 16) return 4;
        return 5; // Maximum 5 days
    }

    /**
     * Select N random days from the available days list
     * Uses Collections.shuffle for randomization
     */
    private List<String> selectRandomDays(int requiredDays, List<String> availableDays) {
        List<String> shuffled = new ArrayList<>(availableDays);
        Collections.shuffle(shuffled);
        int actualDays = Math.min(requiredDays, shuffled.size());
        return shuffled.subList(0, actualDays);
    }

    /**
     * Order courses in round-robin fashion across days
     * This helps distribute courses evenly: Day1, Day2, Day3, Day1, Day2, Day3, ...
     * The ordering influences which day each course gets assigned to
     */
    private List<CSPVariable> orderCoursesRoundRobin(List<CSPVariable> variables, int numDays) {
        // Round-robin is implicit in CSP - we just return as-is
        // The CSP will naturally distribute across days based on domain
        // But we could add more sophisticated ordering here if needed
        return variables;
    }

    private static class CSPVariable {
        Course course;
        List<TimeSlot> domain;

        public CSPVariable(Course course, List<TimeSlot> domain) {
            this.course = course;
            this.domain = domain;
        }
    }

    private static class TimeSlot {
        String day;
        String time;
        String room;

        public TimeSlot(String day, String time, String room) {
            this.day = day;
            this.time = time;
            this.room = room;
        }
    }
}

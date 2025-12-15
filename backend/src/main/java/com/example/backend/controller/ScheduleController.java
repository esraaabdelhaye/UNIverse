package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.TimetableRequest;
import com.example.backend.entity.Schedule;
import com.example.backend.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateTimetable(@RequestBody TimetableRequest request) {
        try {
            System.out.println("=========================================");
            System.out.println("Timetable Generation Request Received");
            System.out.println("Semester: " + request.getSemester());
            System.out.println("Academic Year: " + request.getAcademicYear());
            System.out.println("Course IDs: " + request.getCourseIds());
            System.out.println("Rooms: " + request.getRooms());
            System.out.println("Days: " + request.getDays());
            System.out.println("Time Slots: " + request.getTimeSlots());
            System.out.println("=========================================");
            
            List<Schedule> schedule = scheduleService.generateTimetable(request);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            System.err.println("TIMETABLE GENERATION ERROR:");
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(java.util.Map.of("message", e.getMessage() != null ? e.getMessage() : "Unknown error", 
                                       "error", e.getClass().getSimpleName()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedule(
            @RequestParam(defaultValue = "Fall 2024") String semester,
            @RequestParam(required = false) String academicYear) {
        return ResponseEntity.ok(scheduleService.getSchedule(semester, academicYear));
    }

    @PostMapping("/manual")
    public ResponseEntity<Schedule> addManualSlot(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.addManualSlot(schedule));
    }

    @org.springframework.web.bind.annotation.PutMapping("/manual/{id}")
    public ResponseEntity<Schedule> updateManualSlot(@org.springframework.web.bind.annotation.PathVariable Long id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.updateManualSlot(id, schedule));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@org.springframework.web.bind.annotation.PathVariable Long id) {
        scheduleService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}

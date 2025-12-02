package com.example.backend.controller;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.PerformanceMetricsDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorDTO>>> getAllSupervisors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<DoctorDTO>> response = supervisorService.getAllSupervisors(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getSupervisorById(@PathVariable Long id) {
        ApiResponse<DoctorDTO> response = supervisorService.getSupervisorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getSupervisorByEmail(@PathVariable String email) {
        ApiResponse<DoctorDTO> response = supervisorService.getSupervisorByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorDTO>> createSupervisor(@RequestBody DoctorDTO supervisorDTO) {
        ApiResponse<DoctorDTO> response = supervisorService.registerSupervisor(supervisorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> updateSupervisor(
            @PathVariable Long id,
            @RequestBody DoctorDTO supervisorDTO) {
        ApiResponse<DoctorDTO> response = supervisorService.updateSupervisor(id, supervisorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupervisor(@PathVariable Long id) {
        ApiResponse<Void> response = supervisorService.deleteSupervisor(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/coordinated-courses")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCoordinatedCourses(@PathVariable Long id) {
        ApiResponse<List<CourseDTO>> response = supervisorService.getCoordinatedCourses(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/courses/{courseId}")
    public ResponseEntity<ApiResponse<String>> assignCourseToCoordinator(
            @PathVariable Long id,
            @PathVariable Long courseId) {
        ApiResponse<String> response = supervisorService.assignCourseToCoodinator(id, courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/performance/metrics")
    public ResponseEntity<ApiResponse<PerformanceMetricsDTO>> getSystemPerformance() {
        ApiResponse<PerformanceMetricsDTO> response = supervisorService.getSystemPerformance();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

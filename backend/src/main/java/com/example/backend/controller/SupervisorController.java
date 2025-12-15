package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.DashboardStatsDTO;
import com.example.backend.dto.PerformanceMetricsDTO;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.SupervisorService;

@RestController
@RequestMapping("/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SupervisorDTO>>> getAllSupervisors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<SupervisorDTO>> response = supervisorService.getAllSupervisors(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupervisorDTO>> getSupervisorById(@PathVariable Long id) {
        ApiResponse<SupervisorDTO> response = supervisorService.getSupervisorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<SupervisorDTO>> getSupervisorByEmail(@PathVariable String email) {
        ApiResponse<SupervisorDTO> response = supervisorService.getSupervisorByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupervisorDTO>> createSupervisor(@RequestBody SupervisorDTO supervisorDTO) {
        ApiResponse<SupervisorDTO> response = supervisorService.registerSupervisor(supervisorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupervisorDTO>> updateSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorDTO supervisorDTO) {
        ApiResponse<SupervisorDTO> response = supervisorService.updateSupervisor(id, supervisorDTO);
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

    @PatchMapping("/courses/{courseId}/status")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourseStatus(
            @PathVariable Long courseId,
            @RequestParam String status) {
        ApiResponse<CourseDTO> response = supervisorService.updateCourseStatus(courseId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/performance/metrics")
    public ResponseEntity<ApiResponse<PerformanceMetricsDTO>> getSystemPerformance() {
        ApiResponse<PerformanceMetricsDTO> response = supervisorService.getSystemPerformance();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats() {
        ApiResponse<DashboardStatsDTO> response = supervisorService.getDashboardStats();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

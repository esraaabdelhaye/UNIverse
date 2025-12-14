package com.example.backend.controller;

import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<GradeDTO>>> getStudentGrades(
            @PathVariable Long studentId) {
        ApiResponse<List<GradeDTO>> response = gradeService.getStudentGrades(studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<GradeDTO>>> getCourseGrades(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<List<GradeDTO>> response = gradeService.getCourseGrades(studentId, courseId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}/average")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Double>> getAverageGrade(@PathVariable Long studentId) {
        ApiResponse<Double> response = gradeService.getAverageGrade(studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}/course/{courseId}/average")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Double>> getCourseAverageGrade(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<Double> response = gradeService.getCourseAverageGrade(studentId, courseId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}/status/{status}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<GradeDTO>>> getGradesByStatus(
            @PathVariable Long studentId,
            @PathVariable String status) {
        ApiResponse<List<GradeDTO>> response = gradeService.getGradesByStatus(studentId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/submission/{submissionId}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<GradeDTO>> updateGrade(
            @PathVariable Long submissionId,
            @RequestParam String grade,
            @RequestParam(required = false) String feedback) {
        ApiResponse<GradeDTO> response = gradeService.updateGrade(submissionId, grade, feedback);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
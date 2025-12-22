package com.example.backend.controller;

import com.example.backend.dto.SubmissionDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping(value = "/student/{studentId}/assignment/{assignmentId}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestParam String submissionFile,
            @RequestParam MultipartFile formData) {
        ApiResponse<SubmissionDTO> response = submissionService.submitAssignment(studentId, assignmentId, submissionFile, formData);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{submissionId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> getSubmission(@PathVariable Long submissionId) {
        ApiResponse<SubmissionDTO> response = submissionService.getSubmission(submissionId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}/assignment/{assignmentId}/status")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSubmissionStatus(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId) {
        ApiResponse<Map<String, Object>> response = submissionService.getSubmissionStatus(studentId, assignmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getStudentSubmissions(@PathVariable Long studentId) {
        ApiResponse<List<SubmissionDTO>> response = submissionService.getStudentSubmissions(studentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/assignment/{assignmentId}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getAssignmentSubmissions(@PathVariable Long assignmentId) {
        ApiResponse<List<SubmissionDTO>> response = submissionService.getAssignmentSubmissions(assignmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{submissionId}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody String submissionFile) {
        ApiResponse<SubmissionDTO> response = submissionService.updateSubmission(submissionId, submissionFile);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/status/{submissionId}")
    @PreAuthorize("hasAnyRole('DOC')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> updateSubmissionStatus(
            @PathVariable Long submissionId,
            @RequestParam String status) {
        ApiResponse<SubmissionDTO> response = submissionService.updateSubmissionStatus(submissionId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/grade/{submissionId}")
    @PreAuthorize("hasAnyRole('DOC')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> updateSubmissionGrade(
            @PathVariable Long submissionId,
            @RequestParam String status,
            @RequestParam String grade) {
        ApiResponse<SubmissionDTO> response = submissionService.updateSubmissionGrade(submissionId, status, grade);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getSubmissionsByStatus(@PathVariable String status) {
        ApiResponse<List<SubmissionDTO>> response = submissionService.getSubmissionsByStatus(status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
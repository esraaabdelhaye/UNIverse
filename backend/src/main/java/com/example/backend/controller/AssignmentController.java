
package com.example.backend.controller;

import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<AssignmentDTO>> getAssignmentById(@PathVariable Long id) {
        ApiResponse<AssignmentDTO> response = assignmentService.getAssignmentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<AssignmentDTO>>> getAssignmentsByCourse(
            @PathVariable Long courseId) {
        ApiResponse<List<AssignmentDTO>> response = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<AssignmentDTO>>> getAllAssignmentsByDueDate() {
        ApiResponse<List<AssignmentDTO>> response = assignmentService.getAssignmentsByDueDate();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}/submissions/count")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Long>> getSubmissionCount(@PathVariable Long id) {
        ApiResponse<Long> response = assignmentService.getSubmissionCount(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<AssignmentDTO>> createAssignment(
            @PathVariable Long courseId,
            @Valid @RequestBody AssignmentDTO assignmentDTO) {
        ApiResponse<AssignmentDTO> response = assignmentService.createAssignment(courseId, assignmentDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<AssignmentDTO>> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentDTO assignmentDTO) {
        ApiResponse<AssignmentDTO> response = assignmentService.updateAssignment(id, assignmentDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Void>> deleteAssignment(@PathVariable Long id) {
        ApiResponse<Void> response = assignmentService.deleteAssignment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
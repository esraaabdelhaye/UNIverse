package com.example.backend.service;

import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Assignment;
import com.example.backend.entity.Course;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.repository.AssignmentRepo;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.AssignmentSubmissionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;
    private final CourseRepo courseRepo;
    private final AssignmentSubmissionRepo submissionRepo;
    private final FileStorageService fileStorageService;

    public AssignmentService(AssignmentRepo assignmentRepo, CourseRepo courseRepo,
                             AssignmentSubmissionRepo submissionRepo,  FileStorageService fileStorageService) {
        this.assignmentRepo = assignmentRepo;
        this.courseRepo = courseRepo;
        this.submissionRepo = submissionRepo;
        this.fileStorageService = fileStorageService;
    }

    // Get all assignments for a course
    public ApiResponse<List<AssignmentDTO>> getAssignmentsByCourse(Long courseId) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            List<AssignmentDTO> assignments = assignmentRepo.findByCourse(courseOpt.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(assignments);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching assignments: " + e.getMessage());
        }
    }

    // Get assignment by ID
    public ApiResponse<AssignmentDTO> getAssignmentById(Long assignmentId) {
        try {
            Optional<Assignment> assignmentOpt = assignmentRepo.findById(assignmentId);
            if (assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Assignment not found");
            }

            return ApiResponse.success(convertToDTO(assignmentOpt.get()));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching assignment: " + e.getMessage());
        }
    }

    // Create assignment
    public ApiResponse<AssignmentDTO> createAssignment(Long courseId, String title, String description, String dueDate, Integer maxScore, List<MultipartFile> files) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            Assignment assignment = new Assignment();
            assignment.setTitle(title);
            assignment.setDescription(description);
            assignment.setDueDate(LocalDateTime.parse(dueDate));
            assignment.setMaxScore(maxScore);
            assignment.setCourse(courseOpt.get());

            Assignment saved = assignmentRepo.save(assignment);

            if (files != null && !files.isEmpty()) {
                List<String> filePaths = fileStorageService.storeAssignments(
                        files.toArray(new MultipartFile[0]),
                        saved.getId()
                );

                saved.setAssignmentPaths(filePaths);
                assignmentRepo.save(saved);
            }

            return ApiResponse.created("Assignment created successfully", convertToDTO(saved));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error creating assignment: " + e.getMessage());
        }
    }

    // Update assignment
    public ApiResponse<AssignmentDTO> updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) {
        try {
            Optional<Assignment> assignmentOpt = assignmentRepo.findById(assignmentId);
            if (assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Assignment not found");
            }

            Assignment assignment = assignmentOpt.get();
            if (assignmentDTO.getAssignmentTitle() != null) {
                assignment.setTitle(assignmentDTO.getAssignmentTitle());
            }
            if (assignmentDTO.getDueDate() != null) {
                assignment.setDueDate(assignmentDTO.getDueDate());
            }

            Assignment updated = assignmentRepo.save(assignment);
            return ApiResponse.success("Assignment updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating assignment: " + e.getMessage());
        }
    }

    // Delete assignment
    public ApiResponse<Void> deleteAssignment(Long assignmentId) {
        try {
            if (!assignmentRepo.existsById(assignmentId)) {
                return ApiResponse.notFound("Assignment not found");
            }

            assignmentRepo.deleteById(assignmentId);
            return ApiResponse.success("Assignment deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting assignment: " + e.getMessage());
        }
    }

    // Get assignments by due date (ordered)
    public ApiResponse<List<AssignmentDTO>> getAssignmentsByDueDate() {
        try {
            List<AssignmentDTO> assignments = assignmentRepo.findByOrderByDueDateAsc().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(assignments);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching assignments: " + e.getMessage());
        }
    }

    // Get submissions count for assignment
    public ApiResponse<Long> getSubmissionCount(Long assignmentId) {
        try {
            Optional<Assignment> assignmentOpt = assignmentRepo.findByIdWithSubmissions(assignmentId);
            if (assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Assignment not found");
            }

            long count = submissionRepo.countByAssignment(assignmentOpt.get());
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error counting submissions: " + e.getMessage());
        }
    }

    private AssignmentDTO convertToDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setAssignmentId(String.valueOf(assignment.getId()));
        dto.setAssignmentTitle(assignment.getTitle());
        dto.setDueDate(assignment.getDueDate());
        dto.setCourseCode(assignment.getCourse().getCourseCode());
        dto.setMaxScore(assignment.getMaxScore());
        dto.setDescription(assignment.getDescription());
        dto.setFilePaths(assignment.getAssignmentPaths());
        return dto;
    }
}
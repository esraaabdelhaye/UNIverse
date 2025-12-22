package com.example.backend.service;

import com.example.backend.dto.SubmissionDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Student;
import com.example.backend.entity.Assignment;
import com.example.backend.repository.AssignmentSubmissionRepo;
import com.example.backend.repository.StudentRepo;
import com.example.backend.repository.AssignmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubmissionService {

    private final AssignmentSubmissionRepo submissionRepo;
    private final StudentRepo studentRepo;
    private final AssignmentRepo assignmentRepo;
    private final AssignmentSubmissionRepo assignmentSubmissionRepo;
    private final FileStorageService fileStorageService;

    public SubmissionService(AssignmentSubmissionRepo submissionRepo, StudentRepo studentRepo,
                             AssignmentRepo assignmentRepo, AssignmentSubmissionRepo assignmentSubmissionRepo,
                             FileStorageService fileStorageService) {
        this.submissionRepo = submissionRepo;
        this.studentRepo = studentRepo;
        this.assignmentRepo = assignmentRepo;
        this.assignmentSubmissionRepo = assignmentSubmissionRepo;
        this.fileStorageService = fileStorageService;
    }

    // Submit assignment
    public ApiResponse<SubmissionDTO> submitAssignment(Long studentId, Long assignmentId, String submissionFile , MultipartFile formData) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Assignment> assignmentOpt = assignmentRepo.findById(assignmentId);

            if (studentOpt.isEmpty() || assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Student or assignment not found");
            }

            // Check if already submitted
            Optional<AssignmentSubmission> existing =
                    submissionRepo.findByStudentAndAssignment(studentOpt.get(), assignmentOpt.get());

            if (existing.isPresent()) {
                return ApiResponse.conflict("Assignment already submitted");
            }

            AssignmentSubmission submission = new AssignmentSubmission();
            submission.setStudent(studentOpt.get());
            submission.setAssignment(assignmentOpt.get());
            submission.setCourse(assignmentOpt.get().getCourse());
            submission.setSubmissionDate(LocalDate.now());
            submission.setStatus("submitted");
            String storedFilePath = fileStorageService.storeFile(formData, "submissions");
            submission.setSubmissionFile(storedFilePath);

            AssignmentSubmission saved = submissionRepo.save(submission);
            return ApiResponse.created("Assignment submitted successfully", convertToDTO(saved));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error submitting assignment: " + e.getMessage());
        }
    }

    // Get submission by ID
    public ApiResponse<SubmissionDTO> getSubmission(Long submissionId) {
        try {
            Optional<AssignmentSubmission> submissionOpt = submissionRepo.findById(submissionId);
            if (submissionOpt.isEmpty()) {
                return ApiResponse.notFound("Submission not found");
            }

            return ApiResponse.success(convertToDTO(submissionOpt.get()));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submission: " + e.getMessage());
        }
    }

    // Get submission status
    public ApiResponse<Map<String, Object>> getSubmissionStatus(Long studentId, Long assignmentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Assignment> assignmentOpt = assignmentRepo.findById(assignmentId);

            if (studentOpt.isEmpty() || assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Student or assignment not found");
            }

            Optional<AssignmentSubmission> submission =
                    submissionRepo.findByStudentAndAssignment(studentOpt.get(), assignmentOpt.get());

            Map<String, Object> status = new HashMap<>();
            status.put("assignmentId", assignmentId);
            status.put("assignmentTitle", assignmentOpt.get().getTitle());

            if (submission.isPresent()) {
                AssignmentSubmission sub = submission.get();
                status.put("submitted", true);
                status.put("submissionDate", sub.getSubmissionDate());
                status.put("status", sub.getStatus());
                status.put("grade", sub.getGrade());
                status.put("isGraded", sub.getGrade() != null);
                status.put("feedback", sub.getFeedback());
            } else {
                status.put("submitted", false);
                status.put("status", "not_submitted");
            }

            return ApiResponse.success(status);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submission status: " + e.getMessage());
        }
    }

    // Get all submissions for student
    public ApiResponse<List<SubmissionDTO>> getStudentSubmissions(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<SubmissionDTO> submissions = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(submissions);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submissions: " + e.getMessage());
        }
    }

    // Get submissions for assignment
    public ApiResponse<List<SubmissionDTO>> getAssignmentSubmissions(Long assignmentId) {
        try {
            Optional<Assignment> assignmentOpt = assignmentRepo.findById(assignmentId);
            if (assignmentOpt.isEmpty()) {
                return ApiResponse.notFound("Assignment not found");
            }

            List<SubmissionDTO> submissions = assignmentSubmissionRepo.findByAssignment(assignmentOpt.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(submissions);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submissions: " + e.getMessage());
        }
    }

    // Update submission
    public ApiResponse<SubmissionDTO> updateSubmission(Long submissionId, String submissionFile) {
        try {
            Optional<AssignmentSubmission> submissionOpt = submissionRepo.findById(submissionId);
            if (submissionOpt.isEmpty()) {
                return ApiResponse.notFound("Submission not found");
            }

            AssignmentSubmission submission = submissionOpt.get();
            submission.setSubmissionFile(submissionFile);
            submission.setSubmissionDate(LocalDate.now());

            AssignmentSubmission updated = submissionRepo.save(submission);
            return ApiResponse.success("Submission updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating submission: " + e.getMessage());
        }
    }

    // Get submissions by status
    public ApiResponse<List<SubmissionDTO>> getSubmissionsByStatus(String status) {
        try {
            List<SubmissionDTO> submissions = submissionRepo.findByStatus(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(submissions);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submissions: " + e.getMessage());
        }
    }

    public ApiResponse<SubmissionDTO> updateSubmissionStatus(Long submissionId, String status) {
        try {
            Optional<AssignmentSubmission> submissionOpt = submissionRepo.findById(submissionId);
            if (submissionOpt.isEmpty()) {
                return ApiResponse.notFound("Submission not found");
            }

            AssignmentSubmission submission = submissionOpt.get();
            submission.setStatus(status);

            AssignmentSubmission updated = submissionRepo.save(submission);
            return ApiResponse.success("Submission updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating submission: " + e.getMessage());
        }
    }

    public ApiResponse<SubmissionDTO> updateSubmissionGrade(Long submissionId, String status, String grade) {
        try {
            Optional<AssignmentSubmission> submissionOpt = submissionRepo.findById(submissionId);
            if (submissionOpt.isEmpty()) {
                return ApiResponse.notFound("Submission not found");
            }

            AssignmentSubmission submission = submissionOpt.get();
            submission.setStatus(status);
            submission.setGrade(grade);

            AssignmentSubmission updated = submissionRepo.save(submission);
            return ApiResponse.success("Submission updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating submission: " + e.getMessage());
        }
    }

    private SubmissionDTO convertToDTO(AssignmentSubmission submission) {
        return new SubmissionDTO(
                submission.getAssignment().getId(),
                String.valueOf(submission.getId()),
                String.valueOf(submission.getStudent().getId()),
                submission.getStudent().getName(),
                submission.getAssignment().getTitle(),
                submission.getSubmissionDate().atTime(0, 0),
                submission.getStatus(),
                submission.getGrade() != null ? Double.parseDouble(submission.getGrade()) : null,
                submission.getSubmissionFile()
        );
    }


}
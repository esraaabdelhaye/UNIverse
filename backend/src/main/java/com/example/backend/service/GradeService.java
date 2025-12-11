package com.example.backend.service;

import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Student;
import com.example.backend.entity.Course;
import com.example.backend.entity.CourseEnrollment;
import com.example.backend.repository.AssignmentSubmissionRepo;
import com.example.backend.repository.StudentRepo;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.CourseEnrollmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeService {

    private final AssignmentSubmissionRepo submissionRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final CourseEnrollmentRepo enrollmentRepo;

    public GradeService(AssignmentSubmissionRepo submissionRepo, StudentRepo studentRepo,
                        CourseRepo courseRepo, CourseEnrollmentRepo enrollmentRepo) {
        this.submissionRepo = submissionRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    // Get all grades for a student
    public ApiResponse<List<GradeDTO>> getStudentGrades(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<GradeDTO> grades = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .filter(sub -> sub.getGrade() != null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(grades);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching grades: " + e.getMessage());
        }
    }

    // Get grades for specific course
    public ApiResponse<List<GradeDTO>> getCourseGrades(Long studentId, Long courseId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
                return ApiResponse.notFound("Student or course not found");
            }

            if (!enrollmentRepo.existsByStudentAndCourse(studentOpt.get(), courseOpt.get())) {
                return ApiResponse.forbidden("Student not enrolled in this course");
            }

            List<GradeDTO> grades = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .filter(sub -> sub.getCourse().getId().equals(courseId) && sub.getGrade() != null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(grades);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching course grades: " + e.getMessage());
        }
    }

    // Get average grade for student
    public ApiResponse<Double> getAverageGrade(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<AssignmentSubmission> submissions = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .filter(sub -> sub.getGrade() != null)
                    .collect(Collectors.toList());

            if (submissions.isEmpty()) {
                return ApiResponse.success(0.0);
            }

            double average = submissions.stream()
                    .mapToDouble(sub -> Double.parseDouble(sub.getGrade()))
                    .average()
                    .orElse(0.0);

            return ApiResponse.success(average);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error calculating average: " + e.getMessage());
        }
    }

    // Get course average for student
    public ApiResponse<Double> getCourseAverageGrade(Long studentId, Long courseId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
                return ApiResponse.notFound("Student or course not found");
            }

            List<AssignmentSubmission> submissions = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .filter(sub -> sub.getCourse().getId().equals(courseId) && sub.getGrade() != null)
                    .collect(Collectors.toList());

            if (submissions.isEmpty()) {
                return ApiResponse.success(0.0);
            }

            double average = submissions.stream()
                    .mapToDouble(sub -> Double.parseDouble(sub.getGrade()))
                    .average()
                    .orElse(0.0);

            return ApiResponse.success(average);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error calculating average: " + e.getMessage());
        }
    }

    // Update grade (for instructor)
    public ApiResponse<GradeDTO> updateGrade(Long submissionId, String grade, String feedback) {
        try {
            Optional<AssignmentSubmission> submissionOpt = submissionRepo.findById(submissionId);
            if (submissionOpt.isEmpty()) {
                return ApiResponse.notFound("Submission not found");
            }

            AssignmentSubmission submission = submissionOpt.get();
            submission.setGrade(grade);
            submission.setFeedback(feedback);
            submission.setStatus("graded");

            AssignmentSubmission updated = submissionRepo.save(submission);
            return ApiResponse.success("Grade updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating grade: " + e.getMessage());
        }
    }

    // Get grades by status
    public ApiResponse<List<GradeDTO>> getGradesByStatus(Long studentId, String status) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<GradeDTO> grades = submissionRepo.findByStudent(studentOpt.get()).stream()
                    .filter(sub -> sub.getStatus().equals(status))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(grades);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching grades: " + e.getMessage());
        }
    }

    private GradeDTO convertToDTO(AssignmentSubmission submission) {
        GradeDTO dto = new GradeDTO();
        dto.setGradeId(String.valueOf(submission.getId()));
        dto.setStudentId(String.valueOf(submission.getStudent().getId()));
        dto.setStudentName(submission.getStudent().getName());
        dto.setCourseCode(submission.getCourse().getCourseCode());
        dto.setCourseTitle(submission.getCourse().getName());
        dto.setScore(submission.getGrade() != null ? Double.parseDouble(submission.getGrade()) : null);
        dto.setFeedback(submission.getFeedback());
        dto.setGradingStatus(submission.getStatus());
        dto.setGradedDate(submission.getSubmissionDate().atTime(0, 0));
        return dto;
    }
}
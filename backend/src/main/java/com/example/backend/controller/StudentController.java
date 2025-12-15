package com.example.backend.controller;

import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.request.RegisterStudentRequest;
import com.example.backend.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<StudentDTO>> response = studentService.getAllStudents(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        ApiResponse<StudentDTO> response = studentService.getStudentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentByEmail(@PathVariable String email) {
        ApiResponse<StudentDTO> response = studentService.getStudentByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all courses for a specific student (enrolled courses only)
     */
    @GetMapping("/{studentId}/courses")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getStudentCourses(@PathVariable Long studentId) {
        ApiResponse<List<CourseDTO>> response = studentService.getStudentCourses(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all assignments for a student (from enrolled courses)
     */
    @GetMapping("/{studentId}/assignments")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<AssignmentDTO>>> getStudentAssignments(@PathVariable Long studentId) {
        ApiResponse<List<AssignmentDTO>> response = studentService.getStudentAssignments(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all grades for a student
     */
    @GetMapping("/{studentId}/grades")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<GradeDTO>>> getStudentGrades(@PathVariable Long studentId) {
        ApiResponse<List<GradeDTO>> response = studentService.getStudentGrades(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Enroll a student in a course
     */
    @PostMapping("/{studentId}/enroll/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<String>> enrollInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<String> response = studentService.enrollInCourse(studentId, courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Unenroll a student from a course
     */
    @PostMapping("/{studentId}/unenroll/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<String>> unenrollFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<String> response = studentService.unenrollFromCourse(studentId, courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(
            @RequestBody RegisterStudentRequest registerStudentRequest,
            HttpServletRequest request,
            HttpServletResponse res) {
        ApiResponse<StudentDTO> response = studentService.registerStudent(registerStudentRequest, request, res);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO) {
        ApiResponse<StudentDTO> response = studentService.updateStudent(id, studentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        ApiResponse<Void> response = studentService.deleteStudent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
package com.example.backend.controller;

import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.request.RegisterStudentRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Register a new student
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<StudentDTO>> registerStudent(@RequestBody RegisterStudentRequest request) {
        ApiResponse<StudentDTO> response = studentService.registerStudent(request);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get student by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        ApiResponse<StudentDTO> response = studentService.getStudentById(id);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get student by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentByEmail(@PathVariable String email) {
        ApiResponse<StudentDTO> response = studentService.getStudentByEmail(email);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get student by academic ID
     */
    @GetMapping("/academic-id/{academicId}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentByAcademicId(@PathVariable Long academicId) {
        ApiResponse<StudentDTO> response = studentService.getStudentByAcademicId(academicId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get all students with pagination
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Pageable pageable) {
        ApiResponse<Page<StudentDTO>> response = studentService.getAllStudents(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get students by department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsByDepartment(@PathVariable Long departmentId) {
        ApiResponse<List<StudentDTO>> response = studentService.getStudentsByDepartment(departmentId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Update student information
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO) {
        ApiResponse<StudentDTO> response = studentService.updateStudent(id, studentDTO);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a student
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        ApiResponse<Void> response = studentService.deleteStudent(id);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Enroll student in a course
     */
    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<ApiResponse<String>> enrollInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<String> response = studentService.enrollInCourse(studentId, courseId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Unenroll student from a course
     */
    @PostMapping("/{studentId}/unenroll/{courseId}")
    public ResponseEntity<ApiResponse<String>> unenrollFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ApiResponse<String> response = studentService.unenrollFromCourse(studentId, courseId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all courses for a student
     */
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getStudentCourses(@PathVariable Long studentId) {
        ApiResponse<List<CourseDTO>> response = studentService.getStudentCourses(studentId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get all assignments for a student's courses
     */
    @GetMapping("/{studentId}/assignments")
    public ResponseEntity<ApiResponse<List<AssignmentDTO>>> getStudentAssignments(@PathVariable Long studentId) {
        ApiResponse<List<AssignmentDTO>> response = studentService.getStudentAssignments(studentId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get all submissions for a student
     */
    @GetMapping("/{studentId}/submissions")
    public ResponseEntity<ApiResponse<List<AssignmentSubmission>>> getStudentSubmissions(@PathVariable Long studentId) {
        ApiResponse<List<AssignmentSubmission>> response = studentService.getStudentSubmissions(studentId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Get all grades for a student
     */
    @GetMapping("/{studentId}/grades")
    public ResponseEntity<ApiResponse<List<GradeDTO>>> getStudentGrades(@PathVariable Long studentId) {
        ApiResponse<List<GradeDTO>> response = studentService.getStudentGrades(studentId);
        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<List<CourseDTO>> response = courseService.getAllCourses(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(@PathVariable Long id) {
        ApiResponse<CourseDTO> response = courseService.getCourseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseByCode(@PathVariable String code) {
        ApiResponse<CourseDTO> response = courseService.getCourseByCode(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCoursesBySemester(@PathVariable String semester) {
        ApiResponse<List<CourseDTO>> response = courseService.getCoursesBySemester(semester);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCoursesByDepartment(@PathVariable Long departmentId) {
        ApiResponse<List<CourseDTO>> response = courseService.getCoursesByDepartment(departmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseDTO>> createCourse(@RequestBody CourseDTO courseDTO) {
        ApiResponse<CourseDTO> response = courseService.createCourse(courseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseDTO courseDTO) {
        ApiResponse<CourseDTO> response = courseService.updateCourse(id, courseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        ApiResponse<Void> response = courseService.deleteCourse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> searchCourses(
            @RequestParam String name) {
        ApiResponse<List<CourseDTO>> response = courseService.searchCourses(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourseStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        ApiResponse<CourseDTO> response = courseService.updateCourseStatus(id, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

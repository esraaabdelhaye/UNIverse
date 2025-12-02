package com.example.backend.controller;

import com.example.backend.dto.TeachingAssistantDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.TeachingAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teaching-assistants")
public class TeachingAssistantController {

    @Autowired
    private TeachingAssistantService taService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TeachingAssistantDTO>>> getAllTAs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<TeachingAssistantDTO>> response = taService.getAllTAs(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeachingAssistantDTO>> getTAById(@PathVariable Long id) {
        ApiResponse<TeachingAssistantDTO> response = taService.getTAById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<TeachingAssistantDTO>> getTAByEmail(@PathVariable String email) {
        ApiResponse<TeachingAssistantDTO> response = taService.getTAByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeachingAssistantDTO>> createTA(@RequestBody TeachingAssistantDTO taDTO) {
        ApiResponse<TeachingAssistantDTO> response = taService.registerTA(taDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeachingAssistantDTO>> updateTA(
            @PathVariable Long id,
            @RequestBody TeachingAssistantDTO taDTO) {
        ApiResponse<TeachingAssistantDTO> response = taService.updateTA(id, taDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTA(@PathVariable Long id) {
        ApiResponse<Void> response = taService.deleteTA(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getTACourses(@PathVariable Long id) {
        ApiResponse<List<CourseDTO>> response = taService.getTACourses(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/courses/{courseId}")
    public ResponseEntity<ApiResponse<String>> assignCourseToTA(
            @PathVariable Long id,
            @PathVariable Long courseId) {
        ApiResponse<String> response = taService.assignCourseToTA(id, courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.example.backend.controller;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorDTO>>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<DoctorDTO>> response = doctorService.getAllDoctors(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(@PathVariable Long id) {
        ApiResponse<DoctorDTO> response = doctorService.getDoctorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorByEmail(@PathVariable String email) {
        ApiResponse<DoctorDTO> response = doctorService.getDoctorByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorDTO>> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        ApiResponse<DoctorDTO> response = doctorService.registerDoctor(doctorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDTO doctorDTO) {
        ApiResponse<DoctorDTO> response = doctorService.updateDoctor(id, doctorDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        ApiResponse<Void> response = doctorService.deleteDoctor(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getDoctorCourses(@PathVariable Long id) {
        ApiResponse<List<CourseDTO>> response = doctorService.getDoctorCourses(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/courses/{courseId}")
    public ResponseEntity<ApiResponse<String>> assignCourseToDDoctor(
            @PathVariable Long id,
            @PathVariable Long courseId) {
        ApiResponse<String> response = doctorService.assignCourseToDOctor(id, courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

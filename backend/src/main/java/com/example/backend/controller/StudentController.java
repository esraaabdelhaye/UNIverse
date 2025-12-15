package com.example.backend.controller;

import com.example.backend.dto.StudentDTO;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@RequestBody RegisterStudentRequest registerStudentRequest , HttpServletRequest request, HttpServletResponse res) {

        ApiResponse<StudentDTO> response = studentService.registerStudent(registerStudentRequest,request,res);
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

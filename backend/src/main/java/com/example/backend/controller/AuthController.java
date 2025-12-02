package com.example.backend.controller;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Student;
import com.example.backend.entity.StudentRepresentative;
import com.example.backend.entity.TeachingAssistant;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.StudentRepo;
import com.example.backend.repository.StudentRepresentativeRepo;
import com.example.backend.repository.TeachingAssistantRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentRepo studentRepo;
    private final DoctorRepo doctorRepo;
    private final TeachingAssistantRepo taRepo;
    private final SupervisorRepo supervisorRepo;
    private final StudentRepresentativeRepo studentRepRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(StudentRepo studentRepo,
                          DoctorRepo doctorRepo,
                          TeachingAssistantRepo taRepo,
                          SupervisorRepo supervisorRepo,
                          StudentRepresentativeRepo studentRepRepo,
                          PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.doctorRepo = doctorRepo;
        this.taRepo = taRepo;
        this.supervisorRepo = supervisorRepo;
        this.studentRepRepo = studentRepRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequest request) {
        System.out.println("AuthController: Login attempt for email: " + request.getEmail() + " with role: " + request.getRole());
        if (request.getEmail() == null || request.getPassword() == null) {
            return ApiResponse.badRequest("Email and password are required");
        }

        String role = request.getRole();
        String email = request.getEmail().trim().toLowerCase();

        try {
            if (role == null) role = "student"; // default to student when not provided
            switch (role.trim().toLowerCase()) {
                case "student": {
                    Optional<Student> opt = studentRepo.findByEmail(email);
                    if (opt.isEmpty()) return ApiResponse.unauthorized("Invalid credentials");
                    Student s = opt.get();
                    if (!passwordEncoder.matches(request.getPassword(), s.getHashedPassword()))
                        return ApiResponse.unauthorized("Invalid credentials");

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", s.getId());
                    data.put("fullName", s.getName());
                    data.put("email", s.getEmail());
                    
                    if (s instanceof StudentRepresentative) {
                        data.put("role", "studentRepresentative");
                    } else {
                        data.put("role", "student");
                    }
                    
                    return ApiResponse.success("Login successful", data);
                }
                case "doctor": {
                    Optional<Doctor> opt = doctorRepo.findByEmail(email);
                    if (opt.isEmpty()) return ApiResponse.unauthorized("Invalid credentials");
                    Doctor d = opt.get();
                    if (!passwordEncoder.matches(request.getPassword(), d.getHashedPassword()))
                        return ApiResponse.unauthorized("Invalid credentials");

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", d.getId());
                    data.put("fullName", d.getName());
                    data.put("email", d.getEmail());
                    
                    if (d instanceof Supervisor) {
                        data.put("role", "supervisor");
                    } else {
                        data.put("role", "doctor");
                    }
                    
                    return ApiResponse.success("Login successful", data);
                }
                case "ta":
                case "teachingassistant":
                case "teaching_assistant": {
                    Optional<TeachingAssistant> opt = taRepo.findByEmail(email);
                    if (opt.isEmpty()) return ApiResponse.unauthorized("Invalid credentials");
                    TeachingAssistant t = opt.get();
                    if (!passwordEncoder.matches(request.getPassword(), t.getHashedPassword()))
                        return ApiResponse.unauthorized("Invalid credentials");

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", t.getId());
                    data.put("fullName", t.getName());
                    data.put("email", t.getEmail());
                    data.put("role", "ta");
                    return ApiResponse.success("Login successful", data);
                }
                case "supervisor": {
                    Optional<Supervisor> opt = supervisorRepo.findByEmail(email);
                    if (opt.isEmpty()) return ApiResponse.unauthorized("Invalid credentials");
                    Supervisor s = opt.get();
                    if (!passwordEncoder.matches(request.getPassword(), s.getHashedPassword()))
                        return ApiResponse.unauthorized("Invalid credentials");

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", s.getId());
                    data.put("fullName", s.getName());
                    data.put("email", s.getEmail());
                    data.put("role", "supervisor");
                    return ApiResponse.success("Login successful", data);
                }
                case "representative":
                case "studentrep":
                case "student-rep":
                case "student representative": {
                    Optional<StudentRepresentative> opt = studentRepRepo.findByEmail(email);
                    if (opt.isEmpty()) return ApiResponse.unauthorized("Invalid credentials");
                    StudentRepresentative r = opt.get();
                    if (!passwordEncoder.matches(request.getPassword(), r.getHashedPassword()))
                        return ApiResponse.unauthorized("Invalid credentials");

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", r.getId());
                    data.put("fullName", r.getName());
                    data.put("email", r.getEmail());
                    data.put("role", "studentRepresentative");
                    return ApiResponse.success("Login successful", data);
                }
                default:
                    return ApiResponse.badRequest("Unknown role: " + role);
            }
        } catch (Exception ex) {
            return ApiResponse.internalServerError("Login failed: " + ex.getMessage());
        }
    }

    @PostMapping("/signup")
    public ApiResponse<Map<String, Object>> signup(@RequestBody com.example.backend.dto.request.SignupRequest request) {
        if (request.getEmail() == null || request.getPassword() == null || request.getFullName() == null) {
            return ApiResponse.badRequest("Email, password, and full name are required");
        }

        String role = request.getRole();
        if (role == null) role = "student";
        String email = request.getEmail().trim().toLowerCase();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        try {
            switch (role.trim().toLowerCase()) {
                case "student": {
                    if (studentRepo.findByEmail(email).isPresent()) {
                        return ApiResponse.badRequest("Email already exists");
                    }
                    Student s = new Student();
                    s.setName(request.getFullName());
                    s.setEmail(email);
                    s.setHashedPassword(encodedPassword);
                    // For simplicity, generating a random academic ID if not provided, or using the one provided
                    s.setAcademicId(request.getAcademicId() != null ? request.getAcademicId() : System.currentTimeMillis());
                    
                    studentRepo.save(s);

                    Map<String, Object> data = new HashMap<>();
                    data.put("id", s.getId());
                    data.put("fullName", s.getName());
                    data.put("email", s.getEmail());
                    data.put("role", "student");
                    return ApiResponse.success("Signup successful", data);
                }
                // Add other roles as needed, keeping it simple for now as requested primarily for student flow
                default:
                    return ApiResponse.badRequest("Signup for role " + role + " is not yet implemented or unknown");
            }
        } catch (Exception ex) {
            return ApiResponse.internalServerError("Signup failed: " + ex.getMessage());
        }
    }
}

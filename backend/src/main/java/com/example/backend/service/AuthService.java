package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder ;
    private final StudentRepo studentRepo ;
    private final StudentRepresentativeRepo studentRepresentativeRepo ;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;
    private final TeachingAssistantRepo taRepo ;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder,
                       StudentRepo studentRepo,
                       StudentRepresentativeRepo studentRepresentativeRepo,
                       DoctorRepo doctorRepo,
                       SupervisorRepo supervisorRepo,
                       TeachingAssistantRepo taRepo)
    {
        this.passwordEncoder = passwordEncoder;
        this.studentRepo = studentRepo;
        this.studentRepresentativeRepo = studentRepresentativeRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
        this.taRepo = taRepo;
    }

    // Generic method delegates login request
    // to appropriate helper method
    // Also take care of its downsides
    public ApiResponse<?> login(LoginRequest loginRequest) {
        return switch (loginRequest.getRole()) {
            case "student" -> loginStudent(loginRequest);
            case "studentRep" -> loginStudentRep(loginRequest);
            case "doctor" -> loginDoctor(loginRequest);
            case "supervisor" -> loginSupervisor(loginRequest) ;
            case "ta" -> loginTA(loginRequest);
            default -> ApiResponse.badRequest("Invalid Role");
        } ;
    }

    // Simple login for now
    // We can change the implementation to use spring security
    // We may also add session management but all that have been mentioned
    // Previously is not required for the project to be functional
    private ApiResponse<StudentDTO> loginStudent(LoginRequest loginRequest) {
        try {

            Optional<Student> studentOpt = studentRepo.findByEmail(loginRequest.getEmail());
            if (studentOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or  password");
            Student student = studentOpt.get();

            // The order for .matches is (raw password , hashed password)
            if (!passwordEncoder.matches(loginRequest.getPassword(),student.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            StudentDTO dto = convertToDTO(student);
            return ApiResponse.success("Student logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<StudentRepresentativeDTO> loginStudentRep(LoginRequest loginRequest) {
        try {

            Optional<StudentRepresentative> studentOpt = studentRepresentativeRepo.findByEmail(loginRequest.getEmail());
            if (studentOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            StudentRepresentative student = studentOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), student.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            StudentRepresentativeDTO dto = convertToDTO(student);
            return ApiResponse.success("Student Representative logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<DoctorDTO> loginDoctor(LoginRequest loginRequest) {
        try {

            Optional<Doctor> doctorOpt = doctorRepo.findByEmail(loginRequest.getEmail());
            if (doctorOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            Doctor doctor = doctorOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), doctor.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            DoctorDTO dto = convertToDTO(doctor);
            return ApiResponse.success("Doctor logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<SupervisorDTO> loginSupervisor(LoginRequest loginRequest) {
        try {

            Optional<Supervisor> supervisorOpt = supervisorRepo.findByEmail(loginRequest.getEmail());
            if (supervisorOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            Supervisor supervisor = supervisorOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), supervisor.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            SupervisorDTO dto = convertToDTO(supervisor);
            return ApiResponse.success("Supervisor logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<TeachingAssistantDTO> loginTA(LoginRequest loginRequest) {
        try {

            Optional<TeachingAssistant> taOpt = taRepo.findByEmail(loginRequest.getEmail());
            if (taOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            TeachingAssistant ta = taOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), ta.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            TeachingAssistantDTO dto = convertToDTO(ta);
            return ApiResponse.success("Teaching-Assistant logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    //Helper method to convert from Entity to DTO
    /*
    Refactor Move them to entity classes
    or create general util class
    Also unify the Model between DTOs and Entities
     */

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(String.valueOf(student.getId()));
        dto.setFullName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setEnrollmentStatus("Active");
        return dto;
    }

    private StudentRepresentativeDTO convertToDTO(StudentRepresentative rep) {
        StudentRepresentativeDTO dto = new StudentRepresentativeDTO();
        dto.setStudentId(String.valueOf(rep.getId()));
        dto.setFullName(rep.getName());
        dto.setEmail(rep.getEmail());
        dto.setDepartment(rep.getDepartment() != null ? rep.getDepartment().getName() : null);
//        dto.setSection(rep.getSection());
//        dto.setSemester(rep.getSemester());
        dto.setRole(rep.getRole());
        dto.setStatus(rep.getStatus());
        return dto;
    }

    private DoctorDTO convertToDTO(Doctor doc) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDepartment(doc.getDepartment().getName());
        dto.setEmail(doc.getEmail());
        dto.setFullName(doc.getName());
//        dto.setDoctorId(doc.get());
        dto.setOfficeLocation( doc.getOfficeLocation());
        dto.setPhoneNumber(doc.getPhoneNumber());
        dto.setQualifications(doc.getExpertise());
        return dto;
    }

    private SupervisorDTO convertToDTO(Supervisor supervisor) {
        SupervisorDTO dto = new SupervisorDTO();
        dto.setDepartment(supervisor.getDepartment().getName());
        dto.setEmail(supervisor.getEmail());
        dto.setFullName(supervisor.getName());
//        dto.setEmployeeId(supervisor.getId());
        dto.setOfficeLocation( supervisor.getOfficeLocation());
        dto.setPositionTitle(supervisor.getTitle());
        return dto;
    }

    private TeachingAssistantDTO convertToDTO(TeachingAssistant teachingAssistant) {
        TeachingAssistantDTO dto = new TeachingAssistantDTO();
        dto.setFullName(teachingAssistant.getName());
        dto.setEmail(teachingAssistant.getEmail());
        dto.setPhoneNumber(teachingAssistant.getPhoneNumber());
        // Department has to be presented
        dto.setDepartment(teachingAssistant.getDepartment().getName());
        return dto;
    }

}

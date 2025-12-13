package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder ;
    private final StudentRepo studentRepo ;
    private final StudentRepresentativeRepo studentRepresentativeRepo ;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;
    private final TeachingAssistantRepo taRepo ;
    private final SecurityContextRepository securityContextRepository ;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder,
                       StudentRepo studentRepo,
                       StudentRepresentativeRepo studentRepresentativeRepo,
                       DoctorRepo doctorRepo,
                       SupervisorRepo supervisorRepo,
                       TeachingAssistantRepo taRepo, SecurityContextRepository securityContextRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.studentRepo = studentRepo;
        this.studentRepresentativeRepo = studentRepresentativeRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
        this.taRepo = taRepo;
        this.securityContextRepository = securityContextRepository;
    }

    // Generic method delegates login request
    // to appropriate helper method
    // Also take care of its downsides
    public ApiResponse<?> login(LoginRequest loginRequest , HttpServletRequest request , HttpServletResponse response) {
        return switch (loginRequest.getRole()) {
            case "student" -> loginStudent(loginRequest ,  request, response);
            case "studentRep" -> loginStudentRep(loginRequest,request, response);
            case "staff" -> loginStaff(loginRequest,request,response) ;
            case "doctor" -> loginDoctor(loginRequest,request, response);
            case "supervisor" -> loginSupervisor(loginRequest,request, response) ;
            case "ta" -> loginTA(loginRequest,request, response);
            default -> ApiResponse.badRequest("Invalid Role");
        } ;
    }

    // Simple login for now
    // We can change the implementation to use spring security
    // We may also add session management but all that have been mentioned
    // Previously is not required for the project to be functional
    private ApiResponse<StudentDTO> loginStudent(LoginRequest loginRequest , HttpServletRequest request , HttpServletResponse response ) {
        try {

            Optional<Student> studentOpt = studentRepo.findByEmail(loginRequest.getEmail());
            if (studentOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or  password");
            Student student = studentOpt.get();

            // The order for .matches is (raw password , hashed password)
            if (!passwordEncoder.matches(loginRequest.getPassword(),student.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            //Assigning Authorities to the user such that we can control their access to different services
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_STUDENT")) ;
            // We set the password to null since we don't want to store password in the session
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(student),null,authorities);
            // 1. Create an empty Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // 2. Set Authentication
            context.setAuthentication(auth);
            // 3. Set it to the Holder
            SecurityContextHolder.setContext(context);

            //Save the context
            securityContextRepository.saveContext(context, request, response);

            StudentDTO dto = convertToDTO(student);
            return ApiResponse.success("Student logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<StudentRepresentativeDTO> loginStudentRep(LoginRequest loginRequest ,HttpServletRequest request , HttpServletResponse response) {
        try {

            Optional<StudentRepresentative> studentOpt = studentRepresentativeRepo.findByEmail(loginRequest.getEmail());
            if (studentOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            StudentRepresentative student = studentOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), student.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            //Assigning Authorities to the user such that we can control their access to different services
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_STUDENTREP")) ;
            // We set the password to null since we don't want to store password in the session
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(student),null,authorities);
            // 1. Create an empty Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // 2. Set Authentication
            context.setAuthentication(auth);
            // 3. Set it to the Holder
            SecurityContextHolder.setContext(context);

            //Save the context
            securityContextRepository.saveContext(context, request, response);

            StudentRepresentativeDTO dto = convertToDTO(student);
            return ApiResponse.success("Student Representative logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<DoctorDTO> loginDoctor(LoginRequest loginRequest,HttpServletRequest request , HttpServletResponse response) {
        try {

            Optional<Doctor> doctorOpt = doctorRepo.findByEmail(loginRequest.getEmail());
            if (doctorOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            Doctor doctor = doctorOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), doctor.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            //Assigning Authorities to the user such that we can control their access to different services
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_DOCTOR")) ;
            // We set the password to null since we don't want to store password in the session
            // Save the DTO instead of the email such that you can get the doctor data easier
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(doctor),null,authorities);
            // 1. Create an empty Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // 2. Set Authentication
            context.setAuthentication(auth);
            // 3. Set it to the Holder
            SecurityContextHolder.setContext(context);

            //Save the context
            securityContextRepository.saveContext(context, request, response);

            DoctorDTO dto = convertToDTO(doctor);
            return ApiResponse.success("Doctor logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error"+e.getMessage());
        }
    }

    private ApiResponse<SupervisorDTO> loginSupervisor(LoginRequest loginRequest,HttpServletRequest request , HttpServletResponse response) {
        try {

            Optional<Supervisor> supervisorOpt = supervisorRepo.findByEmail(loginRequest.getEmail());
            if (supervisorOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            Supervisor supervisor = supervisorOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), supervisor.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            //Assigning Authorities to the user such that we can control their access to different services
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ;
            // We set the password to null since we don't want to store password in the session
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(supervisor),null,authorities);
            // 1. Create an empty Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // 2. Set Authentication
            context.setAuthentication(auth);
            // 3. Set it to the Holder
            SecurityContextHolder.setContext(context);

            //Save the context
            securityContextRepository.saveContext(context, request, response);
            SupervisorDTO dto = convertToDTO(supervisor);
            return ApiResponse.success("Supervisor logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }

    private ApiResponse<TeachingAssistantDTO> loginTA(LoginRequest loginRequest,HttpServletRequest request , HttpServletResponse response) {
        try {

            Optional<TeachingAssistant> taOpt = taRepo.findByEmail(loginRequest.getEmail());
            if (taOpt.isEmpty())
                return ApiResponse.unauthorized("Invalid email or password");
            TeachingAssistant ta = taOpt.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), ta.getHashedPassword()))
                return ApiResponse.unauthorized("Invalid email or password");

            //Assigning Authorities to the user such that we can control their access to different services
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_TA")) ;
            // We set the password to null since we don't want to store password in the session
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(ta),null,authorities);
            // 1. Create an empty Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            // 2. Set Authentication
            context.setAuthentication(auth);
            // 3. Set it to the Holder
            SecurityContextHolder.setContext(context);

            //Save the context
            securityContextRepository.saveContext(context, request, response);

            TeachingAssistantDTO dto = convertToDTO(ta);
            return ApiResponse.success("Teaching-Assistant logged in successfully",dto);
        }
        catch (Exception e) {
            return ApiResponse.internalServerError("Internal Server Error");
        }
    }


    /**
     * Generic method to work with frontend
     */
    private ApiResponse<?> loginStaff(LoginRequest loginRequest,HttpServletRequest request , HttpServletResponse response) {
        Optional<Doctor> doctor = doctorRepo.findByEmail(loginRequest.getEmail());
        if (doctor.isPresent()) {
            return loginDoctor(loginRequest, request, response);
        }

        Optional<TeachingAssistant> ta = taRepo.findByEmail(loginRequest.getEmail());
        if (ta.isPresent()) {
            return loginTA(loginRequest, request, response);
        }
        return ApiResponse.unauthorized("Invalid email or password");
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
        dto.setRole("student");
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
        // Currently no departments exist
//        dto.setDepartment(doc.getDepartment().getName());
        dto.setEmail(doc.getEmail());
        dto.setFullName(doc.getName());
        dto.setDoctorId(String.valueOf(doc.getId()));
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
        dto.setEmployeeId(String.valueOf(supervisor.getId()));
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

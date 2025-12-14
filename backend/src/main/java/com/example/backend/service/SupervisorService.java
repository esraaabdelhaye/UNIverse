package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupervisorService {

    private final SupervisorRepo supervisorRepo;
    private final CourseRepo courseRepo;
    private final DoctorRepo doctorRepo;
    private final TeachingAssistantRepo taRepo;

    public SupervisorService(
            SupervisorRepo supervisorRepo,
            CourseRepo courseRepo,
            DoctorRepo doctorRepo,
            TeachingAssistantRepo taRepo
    ) {
        this.supervisorRepo = supervisorRepo;
        this.courseRepo = courseRepo;
        this.doctorRepo = doctorRepo;
        this.taRepo = taRepo;
    }

    // Get all supervisors
    public ApiResponse<Page<SupervisorDTO>> getAllSupervisors(Pageable pageable) {
        try {
            Page<Supervisor> supervisors = supervisorRepo.findAll(pageable);
            Page<SupervisorDTO> supervisorDTOs = supervisors.map(this::convertToDTO);

            return ApiResponse.success(supervisorDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching supervisors: " + e.getMessage());
        }
    }

    // Get supervisor by ID
    public ApiResponse<SupervisorDTO> getSupervisorById(Long id) {
        try {
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(id);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found with ID: " + id);
            }

            SupervisorDTO dto = convertToDTO(supervisorOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching supervisor: " + e.getMessage());
        }
    }

    // Get supervisor by email
    public ApiResponse<SupervisorDTO> getSupervisorByEmail(String email) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findByEmail(email);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found with email: " + email);
            }

            if (doctorOpt.get() instanceof Supervisor supervisor) {
                SupervisorDTO dto = convertToDTO(supervisor);
                return ApiResponse.success(dto);
            }

            return ApiResponse.notFound("User is not a supervisor");

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching supervisor: " + e.getMessage());
        }
    }

    // Register new supervisor
    public ApiResponse<SupervisorDTO> registerSupervisor(SupervisorDTO request) {
        try {
            if (doctorRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Supervisor with this email already exists");
            }

            Supervisor supervisor = new Supervisor();
            supervisor.setName(request.getFullName());
            supervisor.setEmail(request.getEmail());
            supervisor.setOfficeLocation(request.getOfficeLocation());
            supervisor.setTitle(request.getPositionTitle());

            Supervisor savedSupervisor = supervisorRepo.save(supervisor);

            return ApiResponse.created("Supervisor registered successfully", convertToDTO(savedSupervisor));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering supervisor: " + e.getMessage());
        }
    }

    // Update supervisor
    public ApiResponse<SupervisorDTO> updateSupervisor(Long id, SupervisorDTO supervisorDTO) {
        try {
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(id);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found with ID: " + id);
            }

            Supervisor supervisor = supervisorOpt.get();

            if (supervisorDTO.getFullName() != null) {
                supervisor.setName(supervisorDTO.getFullName());
            }
            if (supervisorDTO.getEmail() != null) {
                supervisor.setEmail(supervisorDTO.getEmail());
            }
            if (supervisorDTO.getOfficeLocation() != null) {
                supervisor.setOfficeLocation(supervisorDTO.getOfficeLocation());
            }
            if (supervisorDTO.getPositionTitle() != null) {
                supervisor.setTitle(supervisorDTO.getPositionTitle());
            }

            Supervisor updatedSupervisor = supervisorRepo.save(supervisor);
            SupervisorDTO dto = convertToDTO(updatedSupervisor);

            return ApiResponse.success("Supervisor updated successfully", dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating supervisor: " + e.getMessage());
        }
    }

    // Delete supervisor
    public ApiResponse<Void> deleteSupervisor(Long id) {
        try {
            if (!supervisorRepo.existsById(id)) {
                return ApiResponse.notFound("Supervisor not found with ID: " + id);
            }

            supervisorRepo.deleteById(id);
            return ApiResponse.success("Supervisor deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting supervisor: " + e.getMessage());
        }
    }

    // Manage coordinated courses
    public ApiResponse<String> assignCourseToCoodinator(Long supervisorId, Long courseId) {
        try {
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found");
            }
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            Supervisor supervisor = supervisorOpt.get();
            Course course = courseOpt.get();

            supervisor.addCoordinatedCourse(course);
            supervisorRepo.save(supervisor);

            return ApiResponse.success("Course assigned to coordinator successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error assigning course: " + e.getMessage());
        }
    }

    // Get coordinated courses
    public ApiResponse<List<CourseDTO>> getCoordinatedCourses(Long supervisorId) {
        try {
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found");
            }

            List<CourseDTO> courseDTOs = supervisorOpt.get().getCoordinatedCourses().stream()
                    .map(this::convertCourseToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(courseDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching coordinated courses: " + e.getMessage());
        }
    }

    // Manage doctors
    public ApiResponse<List<DoctorDTO>> getAllDoctors() {
        try {
            List<Doctor> doctors = doctorRepo.findAll();
            List<DoctorDTO> dtos = doctors.stream()
                    .filter(doctor -> !(doctor instanceof Supervisor))
                    .map(this::convertDoctorToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching doctors: " + e.getMessage());
        }
    }

    public ApiResponse<DoctorDTO> registerDoctor(DoctorDTO request) {
        try {
            if (doctorRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Doctor with this email already exists");
            }

            Doctor doctor = new Doctor();
            doctor.setName(request.getFullName());
            doctor.setEmail(request.getEmail());
            doctor.setPhoneNumber(request.getPhoneNumber());
            doctor.setOfficeLocation(request.getOfficeLocation());
            doctor.setTitle(request.getSpecialization());
            doctor.setExpertise(request.getQualifications());

            Doctor savedDoctor = doctorRepo.save(doctor);
            return ApiResponse.created("Doctor registered successfully", convertDoctorToDTO(savedDoctor));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering doctor: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteDoctor(Long doctorId) {
        try {
            if (!doctorRepo.existsById(doctorId)) {
                return ApiResponse.notFound("Doctor not found with ID: " + doctorId);
            }

            doctorRepo.deleteById(doctorId);
            return ApiResponse.success("Doctor deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting doctor: " + e.getMessage());
        }
    }

    // Manage teaching assistants
    public ApiResponse<List<TeachingAssistantDTO>> getAllTAs() {
        try {
            List<TeachingAssistant> tas = taRepo.findAll();
            List<TeachingAssistantDTO> dtos = tas.stream()
                    .map(this::convertTAToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching TAs: " + e.getMessage());
        }
    }

    public ApiResponse<TeachingAssistantDTO> registerTA(TeachingAssistantDTO request) {
        try {
            if (taRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Teaching Assistant with this email already exists");
            }

            TeachingAssistant ta = new TeachingAssistant();
            ta.setName(request.getFullName());
            ta.setEmail(request.getEmail());
            ta.setPhoneNumber(request.getPhoneNumber());
            ta.setOfficeLocation(request.getDepartment());
            ta.setTitle("Teaching Assistant");

            TeachingAssistant savedTA = taRepo.save(ta);
            return ApiResponse.created("Teaching Assistant registered successfully", convertTAToDTO(savedTA));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering Teaching Assistant: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteTA(Long taId) {
        try {
            if (!taRepo.existsById(taId)) {
                return ApiResponse.notFound("Teaching Assistant not found with ID: " + taId);
            }

            taRepo.deleteById(taId);
            return ApiResponse.success("Teaching Assistant deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting Teaching Assistant: " + e.getMessage());
        }
    }

    // Manage courses
    public ApiResponse<List<CourseDTO>> getAllCourses() {
        try {
            List<Course> courses = courseRepo.findAll();
            List<CourseDTO> dtos = courses.stream()
                    .map(this::convertCourseToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching courses: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> getCourseById(Long courseId) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + courseId);
            }

            CourseDTO dto = convertCourseToDTO(courseOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching course: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> getCourseByCode(String courseCode) {
        try {
            Optional<Course> courseOpt = courseRepo.findByCourseCode(courseCode);

            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with code: " + courseCode);
            }

            CourseDTO dto = convertCourseToDTO(courseOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching course: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> updateCourseStatus(Long courseId, String status) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + courseId);
            }

            Course course = courseOpt.get();
            course.setName(status);

            Course savedCourse = courseRepo.save(course);
            return ApiResponse.success("Course status updated successfully", convertCourseToDTO(savedCourse));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating course status: " + e.getMessage());
        }
    }

    // Get performance metrics
    public ApiResponse<PerformanceMetricsDTO> getSystemPerformance() {
        try {
            PerformanceMetricsDTO metrics = new PerformanceMetricsDTO(
                    85.5,
                    92.0,
                    150,
                    "2h 15m",
                    98.0,
                    2.5,
                    99.9
            );
            return ApiResponse.success(metrics);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching performance metrics: " + e.getMessage());
        }
    }

    // Helper methods
    private SupervisorDTO convertToDTO(Supervisor supervisor) {
        SupervisorDTO dto = new SupervisorDTO();
        dto.setEmployeeId(String.valueOf(supervisor.getId()));
        dto.setFullName(supervisor.getName());
        dto.setEmail(supervisor.getEmail());
        dto.setPositionTitle(supervisor.getTitle());
        dto.setOfficeLocation(supervisor.getOfficeLocation());
        if (supervisor.getDepartment() != null) {
            dto.setDepartment(supervisor.getDepartment().getName());
        }
        return dto;
    }

    private DoctorDTO convertDoctorToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(String.valueOf(doctor.getId()));
        dto.setFullName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setSpecialization(doctor.getTitle());
        dto.setOfficeLocation(doctor.getOfficeLocation());
        dto.setQualifications(doctor.getExpertise());
        dto.setAvailableForConsultation(true);
        return dto;
    }

    private CourseDTO convertCourseToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseTitle(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCapacity(course.getEnrollments().size());
        dto.setEnrolled(course.getEnrollments().size());
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());
        dto.setCourseId(String.valueOf(course.getId()));
        if (course.getDepartment() != null) {
            dto.setDepartment(course.getDepartment().getName());
        }
        return dto;
    }

    private TeachingAssistantDTO convertTAToDTO(TeachingAssistant ta) {
        TeachingAssistantDTO dto = new TeachingAssistantDTO();
        dto.setEmployeeId(String.valueOf(ta.getId()));
        dto.setFullName(ta.getName());
        dto.setEmail(ta.getEmail());
        dto.setRole("Teaching Assistant");
        dto.setPhoneNumber(ta.getPhoneNumber());
        if (ta.getDepartment() != null) {
            dto.setDepartment(ta.getDepartment().getName());
        }
        return dto;
    }
}
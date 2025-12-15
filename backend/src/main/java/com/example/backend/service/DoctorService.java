package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Course;
import com.example.backend.entity.Doctor;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DoctorRepo;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final CourseRepo courseRepo;

    public DoctorService(
            DoctorRepo doctorRepo,
            CourseRepo courseRepo
    ) {
        this.doctorRepo = doctorRepo;
        this.courseRepo = courseRepo;
    }

    // Get all doctors
    public ApiResponse<Page<DoctorDTO>> getAllDoctors(Pageable pageable) {
        try {
            Page<Doctor> doctors = doctorRepo.findAll(pageable);
            Page<DoctorDTO> doctorDTOs = doctors.map(this::convertToDTO);

            return ApiResponse.success(doctorDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching doctors: " + e.getMessage());
        }
    }

    // Get doctor by ID
    public ApiResponse<DoctorDTO> getDoctorById(Long id) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findById(id);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found with ID: " + id);
            }

            DoctorDTO dto = convertToDTO(doctorOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching doctor: " + e.getMessage());
        }
    }

    // Get doctor by email
    public ApiResponse<DoctorDTO> getDoctorByEmail(String email) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findByEmail(email);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found with email: " + email);
            }

            DoctorDTO dto = convertToDTO(doctorOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching doctor: " + e.getMessage());
        }
    }

    // Register new doctor
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
            
            // CRITICAL: Set password - MUST NOT BE NULL
            String password = request.getPassword();
            if (password == null || password.trim().isEmpty()) {
                System.err.println("WARNING: Password is null/empty! Using default password.");
                password = "Change@123"; // Default password if not provided
            }
            doctor.setHashedPassword(password); // TODO: Hash this in production!
            
            // Set status to Active
            doctor.setStatus("Active");

            Doctor savedDoctor = doctorRepo.save(doctor);

            return ApiResponse.created("Doctor registered successfully", convertToDTO(savedDoctor));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering doctor: " + e.getMessage());
        }
    }

    // Update doctor
    public ApiResponse<DoctorDTO> updateDoctor(Long id, DoctorDTO doctorDTO) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findById(id);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found with ID: " + id);
            }

            Doctor doctor = doctorOpt.get();

            if (doctorDTO.getFullName() != null) {
                doctor.setName(doctorDTO.getFullName());
            }
            if (doctorDTO.getEmail() != null) {
                doctor.setEmail(doctorDTO.getEmail());
            }
            if (doctorDTO.getPhoneNumber() != null) {
                doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
            }
            if (doctorDTO.getOfficeLocation() != null) {
                doctor.setOfficeLocation(doctorDTO.getOfficeLocation());
            }
            if (doctorDTO.getSpecialization() != null) {
                doctor.setTitle(doctorDTO.getSpecialization());
            }

            Doctor updatedDoctor = doctorRepo.save(doctor);
            DoctorDTO dto = convertToDTO(updatedDoctor);

            return ApiResponse.success("Doctor updated successfully", dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating doctor: " + e.getMessage());
        }
    }

    // Delete doctor (soft delete - sets status to Inactive)
    public ApiResponse<Void> deleteDoctor(Long id) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findById(id);
            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found with ID: " + id);
            }

            Doctor doctor = doctorOpt.get();
            doctor.setStatus("Inactive");
            doctorRepo.save(doctor);

            return ApiResponse.success("Doctor deactivated successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting doctor: " + e.getMessage());
        }
    }

    // Get doctor's courses
    public ApiResponse<List<CourseDTO>> getDoctorCourses(Long doctorId) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found");
            }

            List<CourseDTO> courseDTOs = doctorOpt.get().getCourses().stream()
                    .map(this::convertCourseToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(courseDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching doctor courses: " + e.getMessage());
        }
    }

    // Assign course to doctor
    public ApiResponse<String> assignCourseToDOctor(Long doctorId, Long courseId) {
        try {
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.notFound("Doctor not found");
            }
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            Doctor doctor = doctorOpt.get();
            Course course = courseOpt.get();

            doctor.addCourse(course);
            doctorRepo.save(doctor);

            return ApiResponse.success("Course assigned to doctor successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error assigning course: " + e.getMessage());
        }
    }

    // Helper methods
    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(String.valueOf(doctor.getId()));
        dto.setFullName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setSpecialization(doctor.getTitle());
        dto.setOfficeLocation(doctor.getOfficeLocation());
        dto.setQualifications(doctor.getExpertise());
        if (doctor.getDepartment() != null) {
            dto.setDepartment(doctor.getDepartment().getName());
        }
        if (doctor.getCourses() != null) {
            dto.setCourseCount(doctor.getCourses().size());
        }
        dto.setAvailableForConsultation(true);
        dto.setActive(true);
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
        dto.setId(course.getId());
        if (course.getDepartment() != null) {
            dto.setDepartment(course.getDepartment().getName());
        }
        return dto;
    }
}

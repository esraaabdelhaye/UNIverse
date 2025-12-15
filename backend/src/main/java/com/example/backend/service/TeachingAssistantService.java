package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.TeachingAssistantDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Course;
import com.example.backend.entity.TeachingAssistant;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DepartmentRepo;
import com.example.backend.repository.TeachingAssistantRepo;

@Service
@Transactional
public class TeachingAssistantService {

    private final TeachingAssistantRepo taRepo;
    private final CourseRepo courseRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public TeachingAssistantService(
            TeachingAssistantRepo taRepo,
            CourseRepo courseRepo,
            DepartmentRepo departmentRepo
    ) {
        this.taRepo = taRepo;
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
    }

    public ApiResponse<Page<TeachingAssistantDTO>> getAllTAs(Pageable pageable) {
        try {
            Page<TeachingAssistant> tas = taRepo.findAll(pageable);
            Page<TeachingAssistantDTO> taDTOs = tas.map(this::convertToDTO);

            return ApiResponse.success(taDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching teaching assistants: " + e.getMessage());
        }
    }

    public ApiResponse<TeachingAssistantDTO> getTAById(Long id) {
        try {
            Optional<TeachingAssistant> taOpt = taRepo.findById(id);

            if (taOpt.isEmpty()) {
                return ApiResponse.notFound("Teaching Assistant not found with ID: " + id);
            }

            TeachingAssistantDTO dto = convertToDTO(taOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching teaching assistant: " + e.getMessage());
        }
    }

    public ApiResponse<TeachingAssistantDTO> getTAByEmail(String email) {
        try {
            Optional<TeachingAssistant> taOpt = taRepo.findByEmail(email);

            if (taOpt.isEmpty()) {
                return ApiResponse.notFound("Teaching Assistant not found with email: " + email);
            }

            TeachingAssistantDTO dto = convertToDTO(taOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching teaching assistant: " + e.getMessage());
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
            ta.setTitle(request.getRole());

            TeachingAssistant savedTA = taRepo.save(ta);

            return ApiResponse.created("Teaching Assistant registered successfully", convertToDTO(savedTA));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering teaching assistant: " + e.getMessage());
        }
    }

    public ApiResponse<TeachingAssistantDTO> updateTA(Long id, TeachingAssistantDTO taDTO) {
        try {
            Optional<TeachingAssistant> taOpt = taRepo.findById(id);

            if (taOpt.isEmpty()) {
                return ApiResponse.notFound("Teaching Assistant not found with ID: " + id);
            }

            TeachingAssistant ta = taOpt.get();

            if (taDTO.getFullName() != null) {
                ta.setName(taDTO.getFullName());
            }
            if (taDTO.getEmail() != null) {
                ta.setEmail(taDTO.getEmail());
            }
            if (taDTO.getPhoneNumber() != null) {
                ta.setPhoneNumber(taDTO.getPhoneNumber());
            }
            if (taDTO.getRole() != null) {
                ta.setTitle(taDTO.getRole());
            }

            TeachingAssistant updatedTA = taRepo.save(ta);
            TeachingAssistantDTO dto = convertToDTO(updatedTA);

            return ApiResponse.success("Teaching Assistant updated successfully", dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating teaching assistant: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteTA(Long id) {
        try {
            if (!taRepo.existsById(id)) {
                return ApiResponse.notFound("Teaching Assistant not found with ID: " + id);
            }

            taRepo.deleteById(id);
            return ApiResponse.success("Teaching Assistant deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting teaching assistant: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseDTO>> getTACourses(Long taId) {
        try {
            Optional<TeachingAssistant> taOpt = taRepo.findById(taId);

            if (taOpt.isEmpty()) {
                return ApiResponse.notFound("Teaching Assistant not found");
            }

            List<CourseDTO> courseDTOs = taOpt.get().getAssistedCourses().stream()
                    .map(this::convertCourseToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(courseDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching TA courses: " + e.getMessage());
        }
    }

    public ApiResponse<String> assignCourseToTA(Long taId, Long courseId) {
        try {
            Optional<TeachingAssistant> taOpt = taRepo.findById(taId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (taOpt.isEmpty()) {
                return ApiResponse.notFound("Teaching Assistant not found");
            }
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            TeachingAssistant ta = taOpt.get();
            Course course = courseOpt.get();

            ta.addCourse(course);
            taRepo.save(ta);

            return ApiResponse.success("Course assigned to teaching assistant successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error assigning course: " + e.getMessage());
        }
    }

    private TeachingAssistantDTO convertToDTO(TeachingAssistant ta) {
        TeachingAssistantDTO dto = new TeachingAssistantDTO();
        dto.setEmployeeId(String.valueOf(ta.getId()));
        dto.setFullName(ta.getName());
        dto.setEmail(ta.getEmail());
        dto.setRole(ta.getTitle());
        dto.setPhoneNumber(ta.getPhoneNumber());
        if (ta.getDepartment() != null) {
            dto.setDepartment(ta.getDepartment().getName());
        }
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

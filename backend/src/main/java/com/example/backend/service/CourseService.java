package com.example.backend.service;

import com.example.backend.dto.CourseDTO;
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
public class CourseService {

    private final CourseRepo courseRepo;
    private final DepartmentRepo departmentRepo;
    private final DoctorRepo doctorRepo;

    @Autowired
    public CourseService(
            CourseRepo courseRepo,
            DepartmentRepo departmentRepo,
            DoctorRepo doctorRepo
    ) {
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
        this.doctorRepo = doctorRepo;
    }

    public ApiResponse<Page<CourseDTO>> getAllCourses(Pageable pageable) {
        try {
            Page<Course> courses = courseRepo.findAll(pageable);
            Page<CourseDTO> courseDTOs = courses.map(this::convertToDTO);
            return ApiResponse.success(courseDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching courses: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> getCourseById(Long id) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(id);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            CourseDTO dto = convertToDTO(courseOpt.get());
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
            CourseDTO dto = convertToDTO(courseOpt.get());
            return ApiResponse.success(dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching course: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseDTO>> getCoursesBySemester(String semester) {
        try {
            List<Course> courses = courseRepo.findBySemester(semester);
            List<CourseDTO> dtos = courses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching courses: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseDTO>> getCoursesByDepartment(Long departmentId) {
        try {
            List<Course> courses = courseRepo.findByDepartmentId(departmentId);
            List<CourseDTO> dtos = courses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching courses: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> createCourse(CourseDTO courseDTO) {
        try {
            if (courseRepo.findByCourseCode(courseDTO.getCourseCode()).isPresent()) {
                return ApiResponse.conflict("Course with this code already exists");
            }
            Course course = new Course();
            course.setCourseCode(courseDTO.getCourseCode());
            course.setName(courseDTO.getCourseTitle());
            course.setDescription(courseDTO.getDescription());
            course.setCredits(courseDTO.getCredits());
            course.setSemester(courseDTO.getSemester());
            Course savedCourse = courseRepo.save(course);
            return ApiResponse.created("Course created successfully", convertToDTO(savedCourse));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error creating course: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> updateCourse(Long id, CourseDTO courseDTO) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(id);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            Course course = courseOpt.get();
            if (courseDTO.getCourseTitle() != null) {
                course.setName(courseDTO.getCourseTitle());
            }
            if (courseDTO.getDescription() != null) {
                course.setDescription(courseDTO.getDescription());
            }
            if (courseDTO.getCredits() != null) {
                course.setCredits(courseDTO.getCredits());
            }
            if (courseDTO.getSemester() != null) {
                course.setSemester(courseDTO.getSemester());
            }
            Course updatedCourse = courseRepo.save(course);
            CourseDTO dto = convertToDTO(updatedCourse);
            return ApiResponse.success("Course updated successfully", dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating course: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteCourse(Long id) {
        try {
            if (!courseRepo.existsById(id)) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            courseRepo.deleteById(id);
            return ApiResponse.success("Course deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting course: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseDTO>> searchCourses(String name) {
        try {
            List<Course> courses = courseRepo.findByNameContainingIgnoreCase(name);
            List<CourseDTO> dtos = courses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(dtos);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error searching courses: " + e.getMessage());
        }
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseTitle(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCapacity(course.getEnrollments().size());
        dto.setEnrolled(course.getEnrollments().size());
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());
        if (course.getDepartment() != null) {
            dto.setDepartment(course.getDepartment().getName());
            dto.setInstructorId(String.valueOf(course.getDepartment().getId()));
        }
        return dto;
    }
}

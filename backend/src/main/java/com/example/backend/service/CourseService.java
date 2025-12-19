package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.dto.CourseEnrollmentDTO;
import com.example.backend.entity.CourseEnrollment;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.service.StudentService;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Course;
import com.example.backend.entity.Department;

@Service
@Transactional
public class CourseService {

    private final CourseRepo courseRepo;
    private final DepartmentRepo departmentRepo;
    private final DoctorRepo doctorRepo;
    private final ScheduleRepo scheduleRepo;
    private final AnnouncementRepo announcementRepo;
    @Autowired
    private StudentService studentService;


    @Autowired
    public CourseService(
            CourseRepo courseRepo,
            DepartmentRepo departmentRepo,
            DoctorRepo doctorRepo,
            ScheduleRepo scheduleRepo,
            AnnouncementRepo announcementRepo,
            CourseEnrollmentRepo courseEnrollmentRepo) {
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
        this.doctorRepo = doctorRepo;
        this.scheduleRepo = scheduleRepo;
        this.announcementRepo = announcementRepo;
    }

    public ApiResponse<List<CourseDTO>> getAllCourses(Pageable pageable) {
        try {
            List<Course> courses = courseRepo.findAllWithDoctors();
            List<CourseDTO> courseDTOs = courses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success(courseDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching courses: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> getCourseById(Long id) {
        try {
            Optional<Course> courseOpt = courseRepo.findByIdWithDoctors(id);
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
            Optional<Course> courseOpt = courseRepo.findByCourseCodeWithDoctors(courseCode);
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
            List<Course> courses = courseRepo.findBySemesterWithDoctors(semester);
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
            List<Course> courses = courseRepo.findByDepartmentIdWithDoctors(departmentId);
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

            // Handle Department - more flexible matching
            String deptName = courseDTO.getDepartment();
            Optional<Department> deptOpt = Optional.empty();
            
            if (deptName != null && !deptName.isEmpty()) {
                // Try exact match first
                deptOpt = departmentRepo.findByName(deptName);
                
                // If not found, try with "Department" suffix
                if (deptOpt.isEmpty()) {
                    deptOpt = departmentRepo.findByName(deptName + " Department");
                }
                
                // If still not found, try to find by partial match (contains)
                if (deptOpt.isEmpty()) {
                    List<Department> allDepts = departmentRepo.findAll();
                    deptOpt = allDepts.stream()
                        .filter(d -> d.getName().toLowerCase().contains(deptName.toLowerCase()))
                        .findFirst();
                }
            }
            
            // If still not found, use first available department as fallback
            if (deptOpt.isEmpty()) {
                List<Department> allDepts = departmentRepo.findAll();
                if (!allDepts.isEmpty()) {
                    deptOpt = Optional.of(allDepts.get(0));
                } else {
                    return ApiResponse.badRequest("No departments found in system");
                }
            }
            
            course.setDepartment(deptOpt.get());

            // Set status, default to Open
            if (courseDTO.getStatus() != null && !courseDTO.getStatus().isEmpty()) {
                course.setStatus(courseDTO.getStatus());
            } else {
                course.setStatus("Open");
            }

            Course savedCourse = courseRepo.save(course);
            return ApiResponse.created("Course created successfully", convertToDTO(savedCourse));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error creating course: " + e.getMessage());
        }
    }

    public ApiResponse<CourseDTO> updateCourse(Long id, CourseDTO courseDTO) {
        try {
            Optional<Course> courseOpt = courseRepo.findByIdWithDoctors(id);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            Course course = courseOpt.get();
            if (courseDTO.getCourseTitle() != null) {
                course.setName(courseDTO.getCourseTitle());
            }
            if (courseDTO.getCourseCode() != null && !courseDTO.getCourseCode().isEmpty()) {
                course.setCourseCode(courseDTO.getCourseCode());
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
            if (courseDTO.getDepartment() != null) {
                // Use same flexible matching as createCourse
                String deptName = courseDTO.getDepartment();
                Optional<Department> deptOpt = departmentRepo.findByName(deptName);
                
                // If not found, try with "Department" suffix
                if (deptOpt.isEmpty()) {
                    deptOpt = departmentRepo.findByName(deptName + " Department");
                }
                
                // If still not found, try partial match
                if (deptOpt.isEmpty()) {
                    List<Department> allDepts = departmentRepo.findAll();
                    deptOpt = allDepts.stream()
                        .filter(d -> d.getName().toLowerCase().contains(deptName.toLowerCase()))
                        .findFirst();
                }
                
                if (deptOpt.isPresent()) {
                    course.setDepartment(deptOpt.get());
                }
            }
            if (courseDTO.getStatus() != null) {
                course.setStatus(courseDTO.getStatus());
            }
            if (courseDTO.getCapacity() != null) {
                course.setCapacity(courseDTO.getCapacity());
            }
            
            Course updatedCourse = courseRepo.save(course);
            CourseDTO dto = convertToDTO(updatedCourse);
            return ApiResponse.success("Course updated successfully", dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating course: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Void> deleteCourse(Long id) {
        try {
            Optional<Course> courseOpt = courseRepo.findByIdWithDoctors(id);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            
            // Delete all related entities manually in the correct order
            // 1. Delete announcements
            announcementRepo.deleteAllByCourseId(id);
            
            // 2. Delete schedules
            scheduleRepo.deleteAllByCourseId(id);
            
            // 3. Delete junction table entries (clear() doesn't work!)
            courseRepo.deleteDoctorCourseByCourseId(id);
            courseRepo.deleteTaCourseByCourseId(id);
            courseRepo.deleteCoordinatorCourseByCourseId(id);
            courseRepo.deleteCoursePrerequisitesByCourseId(id);
            courseRepo.deleteSemesterCoursesByCourseId(id);
            
            // 4. Delete the course (cascade will handle enrollments, materials, assignments, submissions)
            courseRepo.deleteById(id);
            courseRepo.flush();
            
            return ApiResponse.success("Course deleted successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
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

    public ApiResponse<CourseDTO> updateCourseStatus(Long id, String status) {
        try {
            Optional<Course> courseOpt = courseRepo.findByIdWithDoctors(id);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + id);
            }
            Course course = courseOpt.get();
            course.setStatus(status);
            Course savedCourse = courseRepo.save(course);
            return ApiResponse.success("Course status updated successfully", convertToDTO(savedCourse));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating course status: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseEnrollmentDTO>> getCourseEnrollments(Long courseId) {
        Optional<Course> courseOpt = courseRepo.findById(courseId);
        if (courseOpt.isEmpty()) {
            return ApiResponse.notFound("Course not found with ID: " + courseId);
        }

        Set<CourseEnrollment> enrollments = courseOpt.get().getEnrollments();

        List<CourseEnrollmentDTO> enrollmentDTOs = enrollments.stream()
                .map(e -> toCourseEnrollmentDTO(e, courseOpt.get()))
                .collect(Collectors.toList());

        return ApiResponse.success(enrollmentDTOs);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseTitle(course.getName());
        dto.setDescription(course.getDescription());
        dto.setEnrolled(course.getEnrollments() != null ? course.getEnrollments().size() : 0);
        dto.setCapacity(course.getCapacity() != null ? course.getCapacity() : 50);
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());
        dto.setStatus(course.getStatus() != null ? course.getStatus() : "Open");
        
        // Handle instructor - check if doctors exist
        if (course.getDoctors() != null && !course.getDoctors().isEmpty()) {
            var doctor = course.getDoctors().iterator().next();
            dto.setInstructorId(String.valueOf(doctor.getId()));
            dto.setInstructorName(doctor.getName());
        } else {
            dto.setInstructorId(null);
            dto.setInstructorName(null);
        }
        
        if (course.getDepartment() != null) {
            dto.setDepartment(course.getDepartment().getName());
        }
        return dto;
    }

    private CourseEnrollmentDTO toCourseEnrollmentDTO(CourseEnrollment courseEnrollment, Course course) {
        CourseEnrollmentDTO dto = new CourseEnrollmentDTO();

        dto.setStudent(studentService.convertToDTO(courseEnrollment.getStudent()));
        dto.setCourse(convertToDTO(course));

        dto.setEnrolledStudents(course.getEnrollments() != null ? course.getEnrollments().size() : 0);
        dto.setGrade(courseEnrollment.getGrade());
        return dto;
    }
}


package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.DashboardStatsDTO;
import com.example.backend.dto.PerformanceMetricsDTO;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Course;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.StudentRepo;
import com.example.backend.repository.SupervisorRepo;

@Service
@Transactional
public class SupervisorService {

    private final SupervisorRepo supervisorRepo;
    private final CourseRepo courseRepo;
    private final DoctorRepo doctorRepo;
    private final StudentRepo studentRepo;

    public SupervisorService(
            SupervisorRepo supervisorRepo,
            CourseRepo courseRepo,
            DoctorRepo doctorRepo,
            StudentRepo studentRepo
    ) {
        this.supervisorRepo = supervisorRepo;
        this.courseRepo = courseRepo;
        this.doctorRepo = doctorRepo;
        this.studentRepo = studentRepo;
    }

    // Get all supervisors with pagination
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
            Optional<Supervisor> supervisorOpt = supervisorRepo.findByEmail(email);
            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found with email: " + email);
            }
            SupervisorDTO dto = convertToDTO(supervisorOpt.get());
            return ApiResponse.success(dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching supervisor: " + e.getMessage());
        }
    }

    // Register new supervisor
    public ApiResponse<SupervisorDTO> registerSupervisor(SupervisorDTO request) {
        try {
            if (supervisorRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Supervisor with this email already exists");
            }

            Supervisor supervisor = new Supervisor();
            supervisor.setName(request.getFullName());
            supervisor.setEmail(request.getEmail());
            supervisor.setOfficeLocation(request.getOfficeLocation());
            supervisor.setTitle(request.getPositionTitle());
            supervisor.setSupervisorRole(request.getRole());

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
            if (supervisorDTO.getRole() != null) {
                supervisor.setSupervisorRole(supervisorDTO.getRole());
            }

            Supervisor updatedSupervisor = supervisorRepo.save(supervisor);
            return ApiResponse.success("Supervisor updated successfully", convertToDTO(updatedSupervisor));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating supervisor: " + e.getMessage());
        }
    }

    // Delete supervisor (soft delete by setting status to Inactive)
    public ApiResponse<Void> deleteSupervisor(Long id) {
        try {
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(id);
            if (supervisorOpt.isEmpty()) {
                return ApiResponse.notFound("Supervisor not found with ID: " + id);
            }

            Supervisor supervisor = supervisorOpt.get();
            supervisor.setStatus("Inactive");
            supervisorRepo.save(supervisor);

            return ApiResponse.success("Supervisor deactivated successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting supervisor: " + e.getMessage());
        }
    }

    // Get courses coordinated by supervisor
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

    // Assign course to coordinator
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

    // Update course status (Open/Closed/Full)
    public ApiResponse<CourseDTO> updateCourseStatus(Long courseId, String status) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found with ID: " + courseId);
            }

            Course course = courseOpt.get();
            course.setStatus(status);
            Course updatedCourse = courseRepo.save(course);

            return ApiResponse.success("Course status updated successfully", convertCourseToDTO(updatedCourse));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating course status: " + e.getMessage());
        }
    }

    // Get system performance metrics (dashboard stats)
    public ApiResponse<PerformanceMetricsDTO> getSystemPerformance() {
        try {
            PerformanceMetricsDTO metrics = new PerformanceMetricsDTO();

            // Calculate real metrics from database
            long totalStudents = studentRepo.count();
            long totalDoctors = doctorRepo.count();
            long totalCourses = courseRepo.count();
            long activeCourses = courseRepo.findAll().stream()
                    .filter(c -> "Open".equalsIgnoreCase(c.getStatus()))
                    .count();

            // Pending approvals (courses without status or with pending status)
            long pendingApprovals = courseRepo.findAll().stream()
                    .filter(c -> c.getStatus() == null || "Pending".equalsIgnoreCase(c.getStatus()))
                    .count();

            metrics.setTotalUsers((int) (totalStudents + totalDoctors));
            metrics.setActiveCourses((int) activeCourses);
            metrics.setPendingApprovals((int) pendingApprovals);

            // Placeholder metrics (can be replaced with real calculations)
            metrics.setAvgStudentFeedback(4.2);
            metrics.setCourseSuccessRate(85.5);
            metrics.setPublicationsCount(12);
            metrics.setTimetableGenerationTime("2.3s");
            metrics.setCourseApprovalRate(92.0);
            metrics.setResourceConflictPercentage(3.5);
            metrics.setSystemUptimePercentage(99.9);

            // System alerts
            List<String> alerts = new ArrayList<>();
            if (pendingApprovals > 0) {
                alerts.add(pendingApprovals + " course(s) pending approval");
            }
            if (activeCourses == 0) {
                alerts.add("No active courses for current semester");
            }
            metrics.setSystemAlerts(alerts);

            return ApiResponse.success(metrics);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching performance metrics: " + e.getMessage());
        }
    }

    // Get dashboard stats for supervisor dashboard
    public ApiResponse<DashboardStatsDTO> getDashboardStats() {
        try {
            DashboardStatsDTO stats = new DashboardStatsDTO();

            // Calculate real counts from database
            long totalStudents = studentRepo.count();
            long totalDoctors = doctorRepo.count();
            long totalCourses = courseRepo.count();

            List<Course> allCourses = courseRepo.findAll();
            long activeCourses = allCourses.stream()
                    .filter(c -> "Open".equalsIgnoreCase(c.getStatus()))
                    .count();
            long pendingApprovals = allCourses.stream()
                    .filter(c -> c.getStatus() == null || "Pending".equalsIgnoreCase(c.getStatus()))
                    .count();

            stats.setTotalUsers((int) (totalStudents + totalDoctors));
            stats.setTotalFaculty((int) totalDoctors);
            stats.setTotalStudents((int) totalStudents);
            stats.setActiveCourses((int) activeCourses);
            stats.setPendingApprovals((int) pendingApprovals);
            stats.setTotalCourses((int) totalCourses);

            // Generate alerts based on data conditions
            List<DashboardStatsDTO.DashboardAlert> alerts = new ArrayList<>();

            if (pendingApprovals > 0) {
                alerts.add(new DashboardStatsDTO.DashboardAlert(
                    "warning",
                    "Pending Course Approvals",
                    pendingApprovals + " course(s) require your approval.",
                    "Review Now"
                ));
            }

            if (activeCourses > 0) {
                alerts.add(new DashboardStatsDTO.DashboardAlert(
                    "success",
                    "Active Courses",
                    activeCourses + " course(s) are currently running.",
                    "View All"
                ));
            }

            stats.setAlerts(alerts);

            return ApiResponse.success(stats);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching dashboard stats: " + e.getMessage());
        }
    }

    // Helper: Convert Supervisor entity to DTO
    private SupervisorDTO convertToDTO(Supervisor supervisor) {
        SupervisorDTO dto = new SupervisorDTO();
        dto.setEmployeeId(String.valueOf(supervisor.getId()));
        dto.setFullName(supervisor.getName());
        dto.setEmail(supervisor.getEmail());
        dto.setOfficeLocation(supervisor.getOfficeLocation());
        dto.setPositionTitle(supervisor.getTitle());
        dto.setRole(supervisor.getSupervisorRole());
        if (supervisor.getDepartment() != null) {
            dto.setDepartment(supervisor.getDepartment().getName());
        }
        return dto;
    }

    // Helper: Convert Course entity to DTO
    private CourseDTO convertCourseToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseTitle(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());
        dto.setStatus(course.getStatus());
        dto.setId(course.getId());
        if (course.getDepartment() != null) {
            dto.setDepartment(course.getDepartment().getName());
        }
        if (course.getEnrollments() != null) {
            dto.setEnrolled(course.getEnrollments().size());
        }
        return dto;
    }
}

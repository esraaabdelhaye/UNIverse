package com.example.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.request.RegisterStudentRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Assignment;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Course;
import com.example.backend.entity.CourseEnrollment;
import com.example.backend.entity.Student;
import com.example.backend.repository.AssignmentSubmissionRepo;
import com.example.backend.repository.CourseEnrollmentRepo;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.StudentRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class StudentService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final CourseEnrollmentRepo enrollmentRepo;
    private final AssignmentSubmissionRepo submissionRepo;
    private final SecurityContextRepository securityContextRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(
            StudentRepo studentRepo,
            CourseRepo courseRepo,
            CourseEnrollmentRepo enrollmentRepo,
            AssignmentSubmissionRepo submissionRepo,
            SecurityContextRepository securityContextRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.submissionRepo = submissionRepo;
        this.securityContextRepository = securityContextRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse<StudentDTO> registerStudent(RegisterStudentRequest request, HttpServletRequest req, HttpServletResponse response) {
        try {
            if (studentRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Student with this email already exists");
            }

            if (studentRepo.findByAcademicId(Long.parseLong(request.getStudentId())).isPresent()) {
                return ApiResponse.conflict("Student with this ID already exists");
            }

            Student student = new Student();
            student.setName(request.getFullName());
            student.setEmail(request.getEmail());
            student.setAcademicId(Long.parseLong(request.getStudentId()));
            student.setDateOfBirth(request.getDateOfBirth());
            student.setPhoneNumber(request.getPhoneNumber());
            student.setHashedPassword(passwordEncoder.encode(request.getPassword()));

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
            Authentication auth = new UsernamePasswordAuthenticationToken(convertToDTO(student), null, authorities);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, req, response);

            Student savedStudent = studentRepo.save(student);
            StudentDTO dto = convertToDTO(savedStudent);

            return ApiResponse.created("Student registered successfully", dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering student: " + e.getMessage());
        }
    }

    public ApiResponse<StudentDTO> getStudentById(Long id) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(id);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found with ID: " + id);
            }
            StudentDTO dto = convertToDTO(studentOpt.get());
            return ApiResponse.success(dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student: " + e.getMessage());
        }
    }

    public ApiResponse<StudentDTO> getStudentByEmail(String email) {
        try {
            Optional<Student> studentOpt = studentRepo.findByEmail(email);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found with email: " + email);
            }
            StudentDTO dto = convertToDTO(studentOpt.get());
            return ApiResponse.success(dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student: " + e.getMessage());
        }
    }

    public ApiResponse<Page<StudentDTO>> getAllStudents(Pageable pageable) {
        try {
            Page<Student> students = studentRepo.findAll(pageable);
            Page<StudentDTO> studentDTOs = students.map(this::convertToDTO);
            return ApiResponse.success(studentDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching students: " + e.getMessage());
        }
    }

    /**
     * Get all courses enrolled by a student
     */
    public ApiResponse<List<CourseDTO>> getStudentCourses(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<CourseEnrollment> enrollments = enrollmentRepo.findByStudentWithCourseDoctors(studentOpt.get());
            List<CourseDTO> courseDTOs = enrollments.stream()
                    .map(enrollment -> convertCourseToDTO(enrollment.getCourse()))
                    .collect(Collectors.toList());

            return ApiResponse.success(courseDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student courses: " + e.getMessage());
        }
    }

    /**
     * Get all assignments from courses the student is enrolled in
     */
    public ApiResponse<List<AssignmentDTO>> getStudentAssignments(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<CourseEnrollment> enrollments = enrollmentRepo.findByStudentWithCourseDoctors(studentOpt.get());
            List<AssignmentDTO> assignmentDTOs = enrollments.stream()
                    .flatMap(enrollment -> enrollment.getCourse().getAssignments().stream())
                    .map(this::convertAssignmentToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(assignmentDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching assignments: " + e.getMessage());
        }
    }

    /**
     * Get all grades for a student (from submissions)
     */
    public ApiResponse<List<GradeDTO>> getStudentGrades(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<AssignmentSubmission> submissions = submissionRepo.findByStudent(studentOpt.get());
            List<GradeDTO> gradeDTOs = submissions.stream()
                    .filter(sub -> sub.getGrade() != null)
                    .map(this::convertSubmissionToGradeDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(gradeDTOs);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching grades: " + e.getMessage());
        }
    }

    public ApiResponse<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(id);
            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found with ID: " + id);
            }

            Student student = studentOpt.get();
            if (studentDTO.getFullName() != null) {
                student.setName(studentDTO.getFullName());
            }
            if (studentDTO.getEmail() != null) {
                student.setEmail(studentDTO.getEmail());
            }

            Student updatedStudent = studentRepo.save(student);
            StudentDTO dto = convertToDTO(updatedStudent);

            return ApiResponse.success("Student updated successfully", dto);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating student: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteStudent(Long id) {
        try {
            if (!studentRepo.existsById(id)) {
                return ApiResponse.notFound("Student not found with ID: " + id);
            }
            studentRepo.deleteById(id);
            return ApiResponse.success("Student deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting student: " + e.getMessage());
        }
    }

    public ApiResponse<String> enrollInCourse(Long studentId, Long courseId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            Student student = studentOpt.get();
            Course course = courseOpt.get();

            if (enrollmentRepo.existsByStudentAndCourse(student, course)) {
                return ApiResponse.conflict("Student is already enrolled in this course");
            }

            CourseEnrollment enrollment = new CourseEnrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDate.now());

            enrollmentRepo.save(enrollment);
            return ApiResponse.success("Successfully enrolled in course", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error enrolling in course: " + e.getMessage());
        }
    }

    public ApiResponse<String> unenrollFromCourse(Long studentId, Long courseId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            Optional<Course> courseOpt = courseRepo.findById(courseId);

            if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
                return ApiResponse.notFound("Student or course not found");
            }

            Student student = studentOpt.get();
            Course course = courseOpt.get();

            Optional<CourseEnrollment> enrollmentOpt = enrollmentRepo.findByStudentAndCourse(student, course);
            if (enrollmentOpt.isEmpty()) {
                return ApiResponse.notFound("Enrollment not found");
            }

            enrollmentRepo.delete(enrollmentOpt.get());
            return ApiResponse.success("Successfully unenrolled from course", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error unenrolling from course: " + e.getMessage());
        }
    }

    // Helper methods
    public StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(String.valueOf(student.getId()));
        dto.setFullName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setEnrollmentStatus("Active");
        return dto;
    }

    private CourseDTO convertCourseToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseTitle(course.getName());
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());
        dto.setDescription(course.getDescription());
        dto.setId(course.getId());
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

    private AssignmentDTO convertAssignmentToDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setAssignmentId(String.valueOf(assignment.getId()));
        dto.setAssignmentTitle(assignment.getTitle());
        dto.setDueDate(assignment.getDueDate());
        dto.setCourseCode(assignment.getCourse().getCourseCode());
        dto.setStatus("Pending");
        return dto;
    }

    private GradeDTO convertSubmissionToGradeDTO(AssignmentSubmission submission) {
        GradeDTO dto = new GradeDTO();
        dto.setGradeId(String.valueOf(submission.getId()));
        dto.setStudentId(String.valueOf(submission.getStudent().getId()));
        dto.setStudentName(submission.getStudent().getName());
        dto.setCourseCode(submission.getCourse().getCourseCode());
        dto.setScore(Double.parseDouble(submission.getGrade()));
        dto.setFeedback(submission.getFeedback());
        dto.setGradingStatus(submission.getStatus());
        return dto;
    }
}
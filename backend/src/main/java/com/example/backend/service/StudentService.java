package com.example.backend.service;

import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.request.RegisterStudentRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final CourseEnrollmentRepo enrollmentRepo;
    private final AssignmentSubmissionRepo submissionRepo;

    public StudentService(
            StudentRepo studentRepo,
            CourseRepo courseRepo,
            CourseEnrollmentRepo enrollmentRepo,
            AssignmentSubmissionRepo submissionRepo
    ) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.submissionRepo = submissionRepo;
    }


    public ApiResponse<StudentDTO> registerStudent(RegisterStudentRequest request) {
        try {
            // Check if student already exists by email
            if (studentRepo.findByEmail(request.getStudentEmail()).isPresent()) {
                return ApiResponse.conflict("Student with this email already exists");
            }

            // Check if academic ID already exists
            if (studentRepo.findByAcademicId(Long.parseLong(request.getStudentId())).isPresent()) {
                return ApiResponse.conflict("Student with this ID already exists");
            }

            // Create new student
            Student student = new Student();
            student.setName(request.getFullName());
            student.setEmail(request.getStudentEmail());
            student.setAcademicId(Long.parseLong(request.getStudentId()));

            // Save student
            Student savedStudent = studentRepo.save(student);

            // Convert to DTO
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

    public ApiResponse<StudentDTO> getStudentByAcademicId(Long academicId) {
        try {
            Optional<Student> studentOpt = studentRepo.findByAcademicId(academicId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found with academic ID: " + academicId);
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

    public ApiResponse<List<StudentDTO>> getStudentsByDepartment(Long departmentId) {
        try {
            List<Student> students = studentRepo.findByDepartmentId(departmentId);
            List<StudentDTO> dtos = students.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(dtos);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching students: " + e.getMessage());
        }
    }

    public ApiResponse<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(id);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found with ID: " + id);
            }

            Student student = studentOpt.get();

            // Update fields if they are not null
            if (studentDTO.getFullName() != null) {
                student.setName(studentDTO.getFullName());
            }
            if (studentDTO.getEmail() != null) {
                student.setEmail(studentDTO.getEmail());
            }

            // Save updated student
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

            // Check if already enrolled
            if (enrollmentRepo.existsByStudentAndCourse(student, course)) {
                return ApiResponse.conflict("Student is already enrolled in this course");
            }

            // Create enrollment
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

            Optional<CourseEnrollment> enrollmentOpt =
                    enrollmentRepo.findByStudentAndCourse(student, course);

            if (enrollmentOpt.isEmpty()) {
                return ApiResponse.notFound("Enrollment not found");
            }

            enrollmentRepo.delete(enrollmentOpt.get());

            return ApiResponse.success("Successfully unenrolled from course", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error unenrolling from course: " + e.getMessage());
        }
    }

    public ApiResponse<List<CourseDTO>> getStudentCourses(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<CourseEnrollment> enrollments =
                    enrollmentRepo.findByStudent(studentOpt.get());

            List<CourseDTO> courseDTOs = enrollments.stream()
                    .map(enrollment -> convertCourseToDTO(enrollment.getCourse()))
                    .collect(Collectors.toList());

            return ApiResponse.success(courseDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student courses: " + e.getMessage());
        }
    }

    public ApiResponse<List<AssignmentDTO>> getStudentAssignments(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            // Get all enrollments for student
            List<CourseEnrollment> enrollments =
                    enrollmentRepo.findByStudent(studentOpt.get());

            // Get assignments from all enrolled courses
            List<AssignmentDTO> assignmentDTOs = enrollments.stream()
                    .flatMap(enrollment -> enrollment.getCourse().getAssignments().stream())
                    .map(this::convertAssignmentToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(assignmentDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching assignments: " + e.getMessage());
        }
    }

    public ApiResponse<List<AssignmentSubmission>> getStudentSubmissions(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<AssignmentSubmission> submissions =
                    submissionRepo.findByStudent(studentOpt.get());

            return ApiResponse.success(submissions);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching submissions: " + e.getMessage());
        }
    }

    public ApiResponse<List<GradeDTO>> getStudentGrades(Long studentId) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.notFound("Student not found");
            }

            List<AssignmentSubmission> submissions =
                    submissionRepo.findByStudent(studentOpt.get());

            List<GradeDTO> gradeDTOs = submissions.stream()
                    .filter(sub -> sub.getGrade() != null)
                    .map(this::convertSubmissionToGradeDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(gradeDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching grades: " + e.getMessage());
        }
    }


    // Helper methods
    private StudentDTO convertToDTO(Student student) {
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
        dto.setCourseId(String.valueOf(course.getId()));
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
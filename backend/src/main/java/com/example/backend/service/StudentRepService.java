package com.example.backend.service;

import com.example.backend.dto.AssignmentDTO;
import com.example.backend.dto.CourseDTO;
import com.example.backend.dto.GradeDTO;
import com.example.backend.dto.StudentRepresentativeDTO;
import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Note that Rep is at the end just a Student
// so we can easily reuse student methods with
public class StudentRepService {
    private final StudentRepresentativeRepo repo;
    private final CourseRepo courseRepo;
    private final CourseEnrollmentRepo enrollmentRepo;
    private final AssignmentSubmissionRepo submissionRepo;
    private final StudentGroupRepo studentGroupRepo;
    private final AnnouncementRepo announcementRepo;
    private final PollRepo pollRepo;
    private final PollOptionRepo pollOptionRepo;
    private final PostRepo postRepo;
    private final DepartmentRepo departmentRepo;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;

    public StudentRepService(StudentRepresentativeRepo repo, CourseRepo courseRepo, CourseEnrollmentRepo enrollmentRepo, AssignmentSubmissionRepo submissionRepo, StudentGroupRepo studentGroupRepo, AnnouncementRepo announcementRepo, PollRepo pollRepo, PollOptionRepo pollOptionRepo, PostRepo postRepo, DepartmentRepo departmentRepo, PasswordEncoder passwordEncoder, StudentService studentService) {
        this.repo = repo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.submissionRepo = submissionRepo;
        this.studentGroupRepo = studentGroupRepo;
        this.announcementRepo = announcementRepo;
        this.pollRepo = pollRepo;
        this.pollOptionRepo = pollOptionRepo;
        this.postRepo = postRepo;
        this.departmentRepo = departmentRepo;
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
    }

    // Currently added field for representative
    // Take care this fields doesn't exist in the
    // Request class we have two options to use (authentication context)
    // or simply add it to the request
    // Also these methods maybe refactored and moved to other service classes
    public Announcement createAnnouncement(StudentRepresentative representative, CreateAnnouncementRequest request) {
        // Find the course
        Course course = courseRepo.findByCourseCode(request.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + request.getCourseCode()));

        // Create the announcement entity
        Announcement announcement = new Announcement();
        announcement.setCourse(course);
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setCreatedAt(request.getPublishDate());
        announcement.setVisibility(request.getVisibility());
        announcement.setAttachments(List.of(request.getAttachments()));
        announcement.setStudentRepAuthor(representative);

        // Save to DB
        return announcementRepo.save(announcement);
    }

    // The same issue with the creator not passed in the request
    // The same two option
    public Poll createPoll(StudentRepresentative currentRep, CreatePollRequest request) {
        // Create poll entity
        Poll poll = new Poll();
        poll.setTitle(request.getPollQuestion());
        poll.setStartTime(LocalDateTime.now());
        poll.setEndTime(request.getEndDate());
        poll.setStudentRepresentative(currentRep);

        // Set options
        if (request.getOptions() != null) {
            for (String optionText : request.getOptions()) {
                PollOption option = new PollOption();
                option.setText(optionText);
                option.setVoteCount(0);
                poll.addOption(option);
            }
        }

        // Save poll (cascade will save options)
        return pollRepo.save(poll);
    }

    // This method needs to be refactored as there is a huge
    // inconsistency between it and the request
    public Post createPost(StudentRepresentative currentRep, CreatePostRequest request) {
        Post post = new Post();

        //Currently no Title is sent for the post so
        // we use the content as temporary title
        post.setTitle(request.getPostContent().length() > 50
                ? request.getPostContent().substring(0, 50)
                : request.getPostContent());
        post.setContent(request.getPostContent());
        post.setStatus(request.getPostType());

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        // Add the current student rep as author
        post.addStudentRep(currentRep);

        // TODO: handle attachments and tags when entity supports them

        // Save post
        return postRepo.save(post);
    }

    public StudentGroup createStudentGroup(CreateStudentGroupRequest request) {
        StudentGroup group = new StudentGroup();
        group.setName(request.getGroupName());
        group.setActivity(request.getGroupDescription());
        // group image is not mapped to entity yet; TODO if needed
        // Also the creator is not set for now for simplicity
        // Students list starts empty
        group.setStudents(List.of());

        return studentGroupRepo.save(group);
    }

    public ApiResponse<StudentRepresentativeDTO> registerStudentRep(RegisterStudentRepRequest request) {
        try {
            if (repo.findByEmail(request.getEmail()).isPresent())
                return ApiResponse.conflict("Student Rep with this email already exists");

            if (repo.findByAcademicId(Long.parseLong(request.getStudentId())).isPresent())
                return ApiResponse.conflict("Student Rep with this ID already exists");

            Department dept = departmentRepo.findByName(request.getDepartment())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));

            StudentRepresentative rep = new StudentRepresentative();
            rep.setName(request.getFullName());
            rep.setEmail(request.getEmail());
            rep.setAcademicId(Long.parseLong(request.getStudentId()));
            rep.setPhoneNumber(request.getPhoneNumber());
            rep.setDepartment(dept);
            // Currently these two fields don't exist in DB
            // Create them Later
//            rep.setSection(request.getSection());
//            rep.setSemester(request.getSemester());

            StudentRepresentative saved = repo.save(rep);
            return ApiResponse.created("Student Representative registered successfully", convertToDTO(saved));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering Student Rep: " + e.getMessage());
        }
    }

    public ApiResponse<StudentRepresentativeDTO> login(String email, String rawPassword) {
        try {
            Optional<StudentRepresentative> repOpt = repo.findByEmail(email);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with email: " + email);
            }

            StudentRepresentative rep = repOpt.get();

            // Check password - Hashed Passwords must be compared
            // Using spring methods
            if (!passwordMatches(rawPassword, rep.getHashedPassword())) {
                return ApiResponse.unauthorized("Invalid password");
            }

            // Return DTO
            StudentRepresentativeDTO dto = convertToDTO(rep);
            return ApiResponse.success("Login successful", dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error during login: " + e.getMessage());
        }
    }

    public ApiResponse<StudentRepresentativeDTO> getStudentRepById(Long id) {
        try {
            Optional<StudentRepresentative> repOpt = repo.findById(id);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }

            StudentRepresentativeDTO dto = convertToDTO(repOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching Student Representative: " + e.getMessage());
        }
    }

    public ApiResponse<StudentRepresentativeDTO> getStudentRepByEmail(String email) {
        try {
            Optional<StudentRepresentative> repOpt = repo.findByEmail(email);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with email: " + email);
            }

            StudentRepresentativeDTO dto = convertToDTO(repOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching Student Representative: " + e.getMessage());
        }
    }

    public ApiResponse<StudentRepresentativeDTO> getStudentRepByAcademicId(Long academicId) {
        try {
            Optional<StudentRepresentative> repOpt = repo.findByAcademicId(academicId);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with academic ID: " + academicId);
            }

            StudentRepresentativeDTO dto = convertToDTO(repOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching Student Representative: " + e.getMessage());
        }
    }

    public ApiResponse<Page<StudentRepresentativeDTO>> getAllStudentReps(Pageable pageable) {
        try {
            Page<StudentRepresentative> reps = repo.findAll(pageable);
            Page<StudentRepresentativeDTO> dtos = reps.map(this::convertToDTO);

            return ApiResponse.success(dtos);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching Student Representatives: " + e.getMessage());
        }
    }

    public ApiResponse<List<StudentRepresentativeDTO>> getStudentRepsByDepartment(Long departmentId) {
        try {
            Optional<StudentRepresentative> reps = repo.findByDepartmentId(departmentId);
            List<StudentRepresentativeDTO> dtos = reps.stream()
                    .map(this::convertToDTO)
                    .toList();

            return ApiResponse.success(dtos);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching Student Representatives: " + e.getMessage());
        }
    }

    public ApiResponse<StudentRepresentativeDTO> updateStudentRep(Long id, StudentRepresentativeDTO dto) {
        try {
            Optional<StudentRepresentative> repOpt = repo.findById(id);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }

            StudentRepresentative rep = repOpt.get();

            if (dto.getFullName() != null) {
                rep.setName(dto.getFullName());
            }
            if (dto.getEmail() != null) {
                rep.setEmail(dto.getEmail());
            }

            StudentRepresentative updated = repo.save(rep);
            return ApiResponse.success("Student Representative updated successfully", convertToDTO(updated));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating Student Representative: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteStudentRep(Long id) {
        try {
            if (!repo.existsById(id)) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }
            repo.deleteById(id);
            return ApiResponse.success("Student Representative deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting Student Representative: " + e.getMessage());
        }
    }


    //Delegation to student service instead of repeating logic
    //Also consider using inheritance
    public ApiResponse<List<CourseDTO>> getCourses(Long repId) {
        return studentService.getStudentCourses(repId);
    }

    public ApiResponse<List<AssignmentDTO>> getAssignments(Long repId) {
        return studentService.getStudentAssignments(repId);
    }

    public ApiResponse<List<GradeDTO>> getGrades(Long repId) {
        return studentService.getStudentGrades(repId);
    }

    ApiResponse<String> enrollInCourse(Long studentRepId , Long CourseId) {
        return studentService.enrollInCourse(studentRepId, CourseId);
    }

    public ApiResponse<String> unenrollFromCourse(Long studentRepId, Long courseId) {
        return studentService.unenrollFromCourse(studentRepId, courseId);
    }

    public ApiResponse<List<CourseDTO>> getStudentCourses(Long studentId) {
        return studentService.getStudentCourses(studentId);
    }

    public ApiResponse<List<AssignmentDTO>> getStudentAssignments(Long studentId) {
        return studentService.getStudentAssignments(studentId);
    }

    public ApiResponse<List<AssignmentSubmission>> getStudentSubmissions(Long studentId) {
        return studentService.getStudentSubmissions(studentId);
    }

    public ApiResponse<List<GradeDTO>> getStudentGrades(Long studentId) {
        return studentService.getStudentGrades(studentId);
    }



    // Helper methods
    // Refactor move these to Utils file
    // since they will be used across the services

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

    //Don't forget we use one-way hashed passwords
    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}






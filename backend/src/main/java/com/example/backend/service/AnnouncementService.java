package com.example.backend.service;

import com.example.backend.dto.AnnouncementAuthor;
import com.example.backend.dto.AnnouncementDTO;
import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.request.CreateAnnouncementRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Announcement;
import com.example.backend.entity.Course;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.AnnouncementRepo;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepo announcementRepo;
    private final CourseRepo courseRepo;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;

    @Autowired
    public AnnouncementService(AnnouncementRepo announcementRepo,
                               CourseRepo courseRepo,
                               DoctorRepo doctorRepo,
                               SupervisorRepo supervisorRepo) {
        this.announcementRepo = announcementRepo;
        this.courseRepo = courseRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
    }

    /**
     * Main entry point for creating an announcement
     */
    public ApiResponse<AnnouncementDTO> createAnnouncement(AnnouncementAuthor author, CreateAnnouncementRequest request) {
        if (author instanceof DoctorDTO doctorDTO) {
            return createAnnouncementForDoctor(doctorDTO, request);
        }
        if (author instanceof SupervisorDTO supervisorDTO) {
            return createAnnouncementForSupervisor(supervisorDTO, request);
        }
        return ApiResponse.unauthorized("This user is not authorized");
    }

    /**
     * Announcement Creation methods
     */
    private ApiResponse<AnnouncementDTO> createAnnouncementForDoctor(DoctorDTO doctorDTO, CreateAnnouncementRequest request) {
        try {
            Long doctorId = Long.parseLong(doctorDTO.getDoctorId());
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.badRequest("Doctor not found");
            }

            Doctor doctor = doctorOpt.get();
            Announcement announcement = buildAnnouncementEntity(request);
            announcement.setDoctorAuthor(doctor);

            // Set course if provided
            setCourseIfProvided(announcement, request.getCourseCode());

            announcementRepo.save(announcement);

            return ApiResponse.success("Created announcement successfully", convertToDTO(announcement));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid doctor ID format");
        }
    }

    private ApiResponse<AnnouncementDTO> createAnnouncementForSupervisor(SupervisorDTO supervisorDTO, CreateAnnouncementRequest request) {
        try {
            Long supervisorId = Long.parseLong(supervisorDTO.getEmployeeId());
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.badRequest("Supervisor not found");
            }

            Supervisor supervisor = supervisorOpt.get();
            Announcement announcement = buildAnnouncementEntity(request);
            announcement.setSupervisorAuthor(supervisor);

            // Set course if provided
            setCourseIfProvided(announcement, request.getCourseCode());

            announcementRepo.save(announcement);

            return ApiResponse.success("Created announcement successfully", convertToDTO(announcement));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid supervisor ID format");
        }
    }

    /**
     * Announcement Modification methods
     */
    public ApiResponse<AnnouncementDTO> updateAnnouncement(AnnouncementAuthor author, AnnouncementDTO announcementDTO) {
        try {
            Long announcementId = Long.parseLong(announcementDTO.getAnnouncementId());
            Optional<Announcement> announcementOpt = announcementRepo.findById(announcementId);

            if (announcementOpt.isEmpty()) {
                return ApiResponse.badRequest("Announcement not found");
            }

            Announcement announcement = announcementOpt.get();

            // Verify authorization
            ApiResponse<Void> authCheck = verifyAnnouncementOwnership(author, announcement);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            // Update announcement fields
            if (announcementDTO.getTitle() != null) {
                announcement.setTitle(announcementDTO.getTitle());
            }
            if (announcementDTO.getContent() != null) {
                announcement.setContent(announcementDTO.getContent());
            }
            if (announcementDTO.getVisibility() != null) {
                announcement.setVisibility(announcementDTO.getVisibility());
            }
            if (announcementDTO.getStatus() != null) {
                announcement.setStatus(announcementDTO.getStatus());
            }

            announcement.setUpdatedAt(LocalDateTime.now());

            announcementRepo.save(announcement);

            return ApiResponse.success("Updated announcement successfully", convertToDTO(announcement));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid announcement ID format");
        }
    }

    /**
     * Announcement deletion methods
     */
    public ApiResponse<AnnouncementDTO> deleteAnnouncement(AnnouncementAuthor author, AnnouncementDTO announcementDTO) {
        try {
            System.out.println(announcementDTO);
            Optional<Announcement> announcementOpt = announcementRepo.findById(Long.parseLong(announcementDTO.getAnnouncementId()));
            if (announcementOpt.isEmpty()) return ApiResponse.badRequest("Announcement not found");

            Announcement announcement = announcementOpt.get();
            ApiResponse<Void> authCheck = verifyAnnouncementOwnership(author, announcement);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            announcementRepo.delete(announcement);
            return ApiResponse.success("Deleted announcement successfully", convertToDTO(announcement));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid announcement ID format");
        }
    }

    /**
     * Announcement retrieval methods
     */
    public ApiResponse<AnnouncementDTO> getAnnouncement(String announcementId) {
        try {
            Optional<Announcement> announcementOpt = announcementRepo.findById(Long.parseLong(announcementId));
            if (announcementOpt.isEmpty()) return ApiResponse.badRequest("Announcement not found");

            Announcement announcement = announcementOpt.get();
            return ApiResponse.success("Fetched announcement successfully", convertToDTO(announcement));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid announcement ID format");
        }
    }

    public ApiResponse<List<AnnouncementDTO>> getAllAnnouncements(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
            Page<Announcement> announcementsPage = announcementRepo.findAll(pageable);

            List<AnnouncementDTO> announcementDTOs = announcementsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Announcements retrieved successfully", announcementDTOs);
        } catch (Exception e) {
            return ApiResponse.badRequest("Error retrieving announcements");
        }
    }

    public ApiResponse<List<AnnouncementDTO>> getAnnouncementsByAuthor(AnnouncementAuthor author, int page, int pageSize) {
        try {
            List<Announcement> announcements;

            if (author instanceof DoctorDTO doctorDTO) {
                Long authorId = Long.parseLong(doctorDTO.getDoctorId());
                announcements = announcementRepo.findByDoctorAuthor_Id(authorId);
            } else if (author instanceof SupervisorDTO supervisorDTO) {
                Long authorId = Long.parseLong(supervisorDTO.getEmployeeId());
                announcements = announcementRepo.findBySupervisorAuthor_Id(authorId);
            } else {
                return ApiResponse.unauthorized("Invalid announcement author");
            }

            List<AnnouncementDTO> announcementDTOs = announcements.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Author's announcements retrieved successfully", announcementDTOs);
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }
    }

    public ApiResponse<List<AnnouncementDTO>> getAnnouncementsByStatus(String status) {
        List<Announcement> announcements = announcementRepo.findByStatus(status);

        List<AnnouncementDTO> announcementDTOs = announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Announcements retrieved successfully", announcementDTOs);
    }

    public ApiResponse<List<AnnouncementDTO>> getAnnouncementsByCourse(String courseCode) {
        List<Announcement> announcements = announcementRepo.findByCourse_CourseCode(courseCode);

        List<AnnouncementDTO> announcementDTOs = announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Course announcements retrieved successfully", announcementDTOs);
    }

    public ApiResponse<List<AnnouncementDTO>> searchAnnouncements(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.badRequest("Search keyword cannot be empty");
        }

        List<Announcement> announcements = announcementRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        List<AnnouncementDTO> announcementDTOs = announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Search completed", announcementDTOs);
    }

    private ApiResponse<List<AnnouncementDTO>> getAnnouncementByCourseId(String courseId) {
        try {
            Long courseIdLong = Long.parseLong(courseId);
            Optional<Course> courseOpt = courseRepo.findById(courseIdLong) ;
            if (courseOpt.isEmpty()) {
                return ApiResponse.badRequest("Course not found") ;
            }
            List<Announcement> announcements = announcementRepo.findByCourseId(courseIdLong) ;
            List<AnnouncementDTO> announcementsDto = announcements.stream().map(announcement -> convertToDTO(announcement)).collect(Collectors.toList()) ;
            return ApiResponse.success("announcements retreived successfully",announcementsDto) ;
        }
        catch(NumberFormatException e) {
            return ApiResponse.badRequest("inValid Course ID " + courseId) ;
        }
    }

    /**
     * Helper methods
     */
    private Announcement buildAnnouncementEntity(CreateAnnouncementRequest request) {
        Announcement announcement = new Announcement();

        String content = request.getContent() != null && !request.getContent().trim().isEmpty()
                ? request.getContent()
                : "No content";

        String title = request.getTitle() != null && !request.getTitle().trim().isEmpty()
                ? request.getTitle()
                : "Untitled Announcement";

        announcement.setContent(content);
        announcement.setTitle(title);
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setVisibility(request.getVisibility());
        announcement.setStatus("ACTIVE");

        // Handle attachments safely
        if (request.getAttachments() != null && request.getAttachments().length > 0) {
            announcement.setAttachments(Arrays.asList(request.getAttachments()));
        }

        return announcement;
    }

    private void setCourseIfProvided(Announcement announcement, String courseCode) {
        if (courseCode != null && !courseCode.trim().isEmpty()) {
            Optional<Course> courseOpt = courseRepo.findByCourseCode(courseCode);
            courseOpt.ifPresent(announcement::setCourse);
        }
    }

    private ApiResponse<Void> verifyAnnouncementOwnership(AnnouncementAuthor author, Announcement announcement) {
        try {
            if (author instanceof DoctorDTO doctorDTO) {
                Long requesterId = Long.parseLong(doctorDTO.getDoctorId());
                if (announcement.getDoctorAuthor() == null || !requesterId.equals(announcement.getDoctorAuthor().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            }
            else if (author instanceof SupervisorDTO supervisorDTO) {
                Long requesterId = Long.parseLong(supervisorDTO.getEmployeeId());
                if (announcement.getSupervisorAuthor() == null || !requesterId.equals(announcement.getSupervisorAuthor().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            }
            else return ApiResponse.unauthorized("Invalid author type");
        }
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }

        return ApiResponse.success(null);
    }

    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setAnnouncementId(String.valueOf(announcement.getId()));
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setCreatedDate(announcement.getCreatedAt());
        dto.setStatus(announcement.getStatus());
        dto.setVisibility(announcement.getVisibility());

        // Set course code if available
        if (announcement.getCourse() != null) {
            dto.setCourseCode(announcement.getCourse().getCourseCode());
        }

        // Determine author
        if (announcement.getDoctorAuthor() != null) {
            dto.setCreatedBy(announcement.getDoctorAuthor().getName());
        } else if (announcement.getSupervisorAuthor() != null) {
            dto.setCreatedBy(announcement.getSupervisorAuthor().getName());
        }

        return dto;
    }
}

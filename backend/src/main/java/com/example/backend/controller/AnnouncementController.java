package com.example.backend.controller;

import com.example.backend.dto.AnnouncementAuthor;
import com.example.backend.dto.AnnouncementDTO;
import com.example.backend.dto.request.CreateAnnouncementRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AnnouncementDTO>> createAnnouncement(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestBody CreateAnnouncementRequest request) {
        ApiResponse<AnnouncementDTO> response = announcementService.createAnnouncement(author, request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


    @PostMapping("/update/{announcementId}")
    public ResponseEntity<ApiResponse<AnnouncementDTO>> updateAnnouncement(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @PathVariable String announcementId,
            @RequestBody AnnouncementDTO dto) {
        dto.setAnnouncementId(announcementId);
        ApiResponse<AnnouncementDTO> response = announcementService.updateAnnouncement(author, dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{announcementId}")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    public ResponseEntity<ApiResponse<AnnouncementDTO>> deleteAnnouncement(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @PathVariable String announcementId) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setAnnouncementId(announcementId);
        ApiResponse<AnnouncementDTO> response = announcementService.deleteAnnouncement(author, dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Announcement retrieval endPoints
     */

    @GetMapping("/get/{announcementId}")
    public ResponseEntity<ApiResponse<AnnouncementDTO>> getAnnouncement(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @PathVariable String announcementId) {
        ApiResponse<AnnouncementDTO> response = announcementService.getAnnouncement(announcementId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<AnnouncementDTO>>> getAllAnnouncement(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestParam int page,
            @RequestParam int pageSize) {
        ApiResponse<List<AnnouncementDTO>> response = announcementService.getAllAnnouncements(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/author")
    public ResponseEntity<ApiResponse<List<AnnouncementDTO>>> getAnnouncementsByAuthor(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestParam int page,
            @RequestParam int pageSize) {
        ApiResponse<List<AnnouncementDTO>> response = announcementService.getAnnouncementsByAuthor(author, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/status")
    public ResponseEntity<ApiResponse<List<AnnouncementDTO>>> getAnnouncementsByStatus(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestParam String status) {
        ApiResponse<List<AnnouncementDTO>> response = announcementService.getAnnouncementsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/course")
    public ResponseEntity<ApiResponse<List<AnnouncementDTO>>> getAnnouncementsByCourse(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestParam String courseCode) {
        ApiResponse<List<AnnouncementDTO>> response = announcementService.getAnnouncementsByCourse(courseCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AnnouncementDTO>>> searchAnnouncements(
            @AuthenticationPrincipal AnnouncementAuthor author,
            @RequestParam String keyword) {
        ApiResponse<List<AnnouncementDTO>> response = announcementService.searchAnnouncements(keyword);
        return ResponseEntity.ok(response);
    }
}

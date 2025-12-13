package com.example.backend.controller;

import com.example.backend.dto.AnnouncementAuthor;
import com.example.backend.dto.AnnouncementDTO;
import com.example.backend.dto.request.CreateAnnouncementRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
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
    ApiResponse<AnnouncementDTO> createAnnouncement(@AuthenticationPrincipal AnnouncementAuthor author , @RequestBody CreateAnnouncementRequest request) {
        return announcementService.createAnnouncement(author,request);
    }

    @PostMapping("/update/{announcementId}")
    ApiResponse<AnnouncementDTO> updateAnnouncement(@AuthenticationPrincipal AnnouncementAuthor author , @PathVariable String announcementId, @RequestBody AnnouncementDTO dto) {
        dto.setAnnouncementId(announcementId);
        return announcementService.updateAnnouncement(author,dto);
    }

    // The same as Post will fixed depending on time
    @DeleteMapping("/{announcementId}")
    ApiResponse<AnnouncementDTO> deleteAnnouncement(@AuthenticationPrincipal AnnouncementAuthor author , @PathVariable String announcementId) {
        AnnouncementDTO dto = new AnnouncementDTO() ;
        dto.setAnnouncementId(announcementId);
        return announcementService.deleteAnnouncement(author,dto);
    }

    /**
     * Announcement retrieval endPoints
     */

    @GetMapping("/get/{announcementId}")
    ApiResponse<AnnouncementDTO> getAnnouncement(@AuthenticationPrincipal AnnouncementAuthor author , @PathVariable String announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    @GetMapping("/get/all")
    ApiResponse<List<AnnouncementDTO>> getAllAnnouncement(@AuthenticationPrincipal AnnouncementAuthor author , @RequestParam int page , @RequestParam int pageSize ) {
        return announcementService.getAllAnnouncements(page,pageSize);
    }

    @GetMapping("/get/author")
    ApiResponse<List<AnnouncementDTO>> getAnnouncementsByAuthor(@AuthenticationPrincipal AnnouncementAuthor author , @RequestParam int page , @RequestParam int pageSize ) {
        return announcementService.getAnnouncementsByAuthor(author,page,pageSize);
    }

    @GetMapping("/get/status")
    ApiResponse<List<AnnouncementDTO>> getAnnouncementsByStatus(@AuthenticationPrincipal AnnouncementAuthor author ,  @RequestParam String status ) {
        return announcementService.getAnnouncementsByStatus(status);
    }

    @GetMapping("/get/course")
    ApiResponse<List<AnnouncementDTO>> getAnnouncementsByCourse(@AuthenticationPrincipal AnnouncementAuthor author , @RequestParam String courseCode ) {
        return announcementService.getAnnouncementsByCourse(courseCode);
    }

    @GetMapping("/search")
    ApiResponse<List<AnnouncementDTO>> searchAnnouncements(@AuthenticationPrincipal AnnouncementAuthor author , @RequestParam String keyword ) {
        return announcementService.searchAnnouncements(keyword);
    }

}

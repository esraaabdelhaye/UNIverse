package com.example.backend.controller;

import com.example.backend.dto.EventDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.EventAuthor ;
import com.example.backend.dto.request.CreateEventRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventDTO>> createEvent(
            @AuthenticationPrincipal Object author,
            @RequestBody CreateEventRequest request) {
        ApiResponse<EventDTO> response = eventService.createEvent((EventAuthor) author, request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/update/{eventId}")
    public ResponseEntity<ApiResponse<EventDTO>> updateEvent(
            @AuthenticationPrincipal Object author,
            @PathVariable String eventId,
            @RequestBody EventDTO dto) {
        dto.setEventId(eventId);
        ApiResponse<EventDTO> response = eventService.updateEvent((EventAuthor) author, dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<EventDTO>> deleteEvent(
            @AuthenticationPrincipal Object author,
            @PathVariable String eventId) {
        EventDTO dto = new EventDTO();
        dto.setEventId(eventId);
        ApiResponse<EventDTO> response = eventService.deleteEvent((EventAuthor) author, dto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Event retrieval endpoints
     */

    @GetMapping("/get/{eventId}")
    public ResponseEntity<ApiResponse<EventDTO>> getEvent(
            @AuthenticationPrincipal Object author,
            @PathVariable String eventId) {
        ApiResponse<EventDTO> response = eventService.getEvent(eventId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getAllEvents(
            @AuthenticationPrincipal Object author,
            @RequestParam int page,
            @RequestParam int pageSize) {
        ApiResponse<List<EventDTO>> response = eventService.getAllEvents(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/author")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getEventsByAuthor(
            @AuthenticationPrincipal Object author,
            @RequestParam int page,
            @RequestParam int pageSize) {
        ApiResponse<List<EventDTO>> response = eventService.getEventsByAuthor((EventAuthor) author, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/upcoming")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getUpcomingEvents(
            @AuthenticationPrincipal Object author) {
        ApiResponse<List<EventDTO>> response = eventService.getUpcomingEvents();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/past")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getPastEvents(
            @AuthenticationPrincipal Object author) {
        ApiResponse<List<EventDTO>> response = eventService.getPastEvents();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/location")
    public ResponseEntity<ApiResponse<List<EventDTO>>> getEventsByLocation(
            @AuthenticationPrincipal Object author,
            @RequestParam String location) {
        ApiResponse<List<EventDTO>> response = eventService.getEventsByLocation(location);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EventDTO>>> searchEvents(
            @AuthenticationPrincipal Object author,
            @RequestParam String keyword) {
        ApiResponse<List<EventDTO>> response = eventService.searchEvents(keyword);
        return ResponseEntity.ok(response);
    }
}

package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.EventDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.request.CreateEventRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Event;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.EventRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepo eventRepo;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;

    @Autowired
    public EventService(EventRepo eventRepo, DoctorRepo doctorRepo, SupervisorRepo supervisorRepo) {
        this.eventRepo = eventRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
    }

    /**
     * Main entry point for creating an event
     */
    public ApiResponse<EventDTO> createEvent(PostAuthor author, CreateEventRequest request) {
        if (author instanceof DoctorDTO doctorDTO) {
            return createEventForDoctor(doctorDTO, request);
        }
        if (author instanceof SupervisorDTO supervisorDTO) {
            return createEventForSupervisor(supervisorDTO, request);
        }
        return ApiResponse.unauthorized("This user is not authorized");
    }

    /**
     * Event Creation methods
     */
    private ApiResponse<EventDTO> createEventForDoctor(DoctorDTO doctorDTO, CreateEventRequest request) {
        try {
            Long doctorId = Long.parseLong(doctorDTO.getDoctorId());
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.badRequest("Doctor not found");
            }

            Doctor doctor = doctorOpt.get();
            Event event = buildEventEntity(request);
            event.setDoctor(doctor);

            eventRepo.save(event);

            return ApiResponse.success("Created event successfully", convertToDTO(event));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid doctor ID format");
        }
    }

    private ApiResponse<EventDTO> createEventForSupervisor(SupervisorDTO supervisorDTO, CreateEventRequest request) {
        try {
            Long supervisorId = Long.parseLong(supervisorDTO.getEmployeeId());
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.badRequest("Supervisor not found");
            }

            Supervisor supervisor = supervisorOpt.get();
            Event event = buildEventEntity(request);
            event.setSupervisor(supervisor);

            eventRepo.save(event);

            return ApiResponse.success("Created event successfully", convertToDTO(event));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid supervisor ID format");
        }
    }

    /**
     * Event Modification methods
     */
    public ApiResponse<EventDTO> updateEvent(PostAuthor author, EventDTO eventDTO) {
        try {
            Long eventId = Long.parseLong(eventDTO.getEventId());
            Optional<Event> eventOpt = eventRepo.findById(eventId);

            if (eventOpt.isEmpty()) {
                return ApiResponse.badRequest("Event not found");
            }

            Event event = eventOpt.get();

            // Verify authorization
            ApiResponse<Void> authCheck = verifyEventOwnership(author, event);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            // Update event fields
            if (eventDTO.getEventTitle() != null) {
                event.setTitle(eventDTO.getEventTitle());
            }
            if (eventDTO.getEventDescription() != null) {
                event.setDescription(eventDTO.getEventDescription());
            }
            if (eventDTO.getEventDateTime() != null) {
                event.setStartTime(eventDTO.getEventDateTime());
            }
            if (eventDTO.getLocation() != null) {
                event.setLocation(eventDTO.getLocation());
            }

            eventRepo.save(event);

            return ApiResponse.success("Updated event successfully", convertToDTO(event));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid event ID format");
        }
    }

    /**
     * Event deletion methods
     */
    public ApiResponse<EventDTO> deleteEvent(PostAuthor author, EventDTO eventDTO) {
        try {
            Optional<Event> eventOpt = eventRepo.findById(Long.parseLong(eventDTO.getEventId()));
            if (eventOpt.isEmpty()) return ApiResponse.badRequest("Event not found");

            Event event = eventOpt.get();
            ApiResponse<Void> authCheck = verifyEventOwnership(author, event);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            eventRepo.delete(event);
            return ApiResponse.success("Deleted event successfully", convertToDTO(event));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid event ID format");
        }
    }

    /**
     * Event retrieval methods
     */
    public ApiResponse<EventDTO> getEvent(String eventId) {
        try {
            Optional<Event> eventOpt = eventRepo.findById(Long.parseLong(eventId));
            if (eventOpt.isEmpty()) return ApiResponse.badRequest("Event not found");

            Event event = eventOpt.get();
            return ApiResponse.success("Fetched event successfully", convertToDTO(event));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid event ID format");
        }
    }

    public ApiResponse<List<EventDTO>> getAllEvents(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("startTime").descending());
            Page<Event> eventsPage = eventRepo.findAll(pageable);

            List<EventDTO> eventDTOs = eventsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Events retrieved successfully", eventDTOs);
        } catch (Exception e) {
            return ApiResponse.badRequest("Error retrieving events");
        }
    }

    public ApiResponse<List<EventDTO>> getEventsByAuthor(PostAuthor author, int page, int pageSize) {
        try {
            List<Event> events;

            if (author instanceof DoctorDTO doctorDTO) {
                Long authorId = Long.parseLong(doctorDTO.getDoctorId());
                events = eventRepo.findByDoctor_Id(authorId);
            } else if (author instanceof SupervisorDTO supervisorDTO) {
                Long authorId = Long.parseLong(supervisorDTO.getEmployeeId());
                events = eventRepo.findBySupervisor_Id(authorId);
            } else {
                return ApiResponse.unauthorized("Invalid event author");
            }

            List<EventDTO> eventDTOs = events.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Author's events retrieved successfully", eventDTOs);
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }
    }

    public ApiResponse<List<EventDTO>> getUpcomingEvents() {
        List<Event> events = eventRepo.findByStartTimeAfter(LocalDateTime.now());

        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Upcoming events retrieved successfully", eventDTOs);
    }

    public ApiResponse<List<EventDTO>> getPastEvents() {
        List<Event> events = eventRepo.findByEndTimeBefore(LocalDateTime.now());

        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Past events retrieved successfully", eventDTOs);
    }

    public ApiResponse<List<EventDTO>> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.badRequest("Search keyword cannot be empty");
        }

        List<Event> events = eventRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);

        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Search completed", eventDTOs);
    }

    public ApiResponse<List<EventDTO>> getEventsByLocation(String location) {
        List<Event> events = eventRepo.findByLocationContainingIgnoreCase(location);

        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Events at location retrieved successfully", eventDTOs);
    }

    /**
     * Helper methods
     */
    private Event buildEventEntity(CreateEventRequest request) {
        Event event = new Event();

        String title = request.getEventTitle() != null && !request.getEventTitle().trim().isEmpty()
                ? request.getEventTitle()
                : "Untitled Event";

        String description = request.getEventDescription() != null && !request.getEventDescription().trim().isEmpty()
                ? request.getEventDescription()
                : "No description";

        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(request.getEventDateTime() != null ? request.getEventDateTime() : LocalDateTime.now());
        event.setEndTime(request.getEventEndDateTime() != null ? request.getEventEndDateTime() : LocalDateTime.now().plusHours(1));
        event.setLocation(request.getLocation());

        return event;
    }

    private ApiResponse<Void> verifyEventOwnership(PostAuthor author, Event event) {
        try {
            if (author instanceof DoctorDTO doctorDTO) {
                Long requesterId = Long.parseLong(doctorDTO.getDoctorId());
                if (event.getDoctor() == null || !requesterId.equals(event.getDoctor().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } else if (author instanceof SupervisorDTO supervisorDTO) {
                Long requesterId = Long.parseLong(supervisorDTO.getEmployeeId());
                if (event.getSupervisor() == null || !requesterId.equals(event.getSupervisor().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } else {
                return ApiResponse.unauthorized("Invalid author type");
            }
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }

        return ApiResponse.success(null);
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setEventId(String.valueOf(event.getId()));
        dto.setEventTitle(event.getTitle());
        dto.setEventDescription(event.getDescription());
        dto.setEventDateTime(event.getStartTime());
        dto.setEventEndDateTime(event.getEndTime());
        dto.setLocation(event.getLocation());

        // Determine event type based on time
        if (event.getStartTime() != null) {
            if (event.getStartTime().isAfter(LocalDateTime.now())) {
                dto.setEventType("UPCOMING");
            } else if (event.getEndTime() != null && event.getEndTime().isBefore(LocalDateTime.now())) {
                dto.setEventType("PAST");
            } else {
                dto.setEventType("ONGOING");
            }
        }

        dto.setAttendeeCount(0);
        // Default, can be updated if attendee tracking is added
        // Also note currently the dataBase doesn't keep track of
        // Students attending the event

        return dto;
    }
}

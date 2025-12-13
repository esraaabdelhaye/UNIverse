package com.example.backend.repository;

import com.example.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findByDoctor_Id(Long doctorId);

    List<Event> findBySupervisor_Id(Long supervisorId);

    List<Event> findByStartTimeAfter(LocalDateTime dateTime);

    List<Event> findByEndTimeBefore(LocalDateTime dateTime);

    List<Event> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    List<Event> findByLocationContainingIgnoreCase(String location);
}

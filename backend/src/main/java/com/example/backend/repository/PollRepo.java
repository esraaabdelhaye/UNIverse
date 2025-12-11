package com.example.backend.repository;

import com.example.backend.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PollRepo extends JpaRepository<Poll, Long> {

    List<Poll> findByDoctorCreator_Id(Long doctorId);

    List<Poll> findBySupervisorCreator_Id(Long supervisorId);

    List<Poll> findByEndTimeAfter(LocalDateTime dateTime);

    List<Poll> findByTitleContainingIgnoreCase(String title);
}

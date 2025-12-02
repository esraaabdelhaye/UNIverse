package com.example.backend.repository;

import com.example.backend.entity.TeachingAssistant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeachingAssistantRepo extends JpaRepository<TeachingAssistant,Long> {

    Optional<TeachingAssistant> findByEmail(String email);

}

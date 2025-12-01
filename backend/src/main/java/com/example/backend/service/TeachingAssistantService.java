package com.example.backend.service;

import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Course;
import com.example.backend.entity.Question;
import com.example.backend.entity.TeachingAssistant;
import com.example.backend.repository.AssignmentSubmissionRepo;
import com.example.backend.repository.TeachingAssistantRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeachingAssistantService {

    @Autowired
    private TeachingAssistantRepo taRepo;

    @Autowired
    private AssignmentSubmissionRepo assignmentRepo;

    // Simplified Service Method
    public Set<Course> getAssistedCourses(long taId) {
        TeachingAssistant ta = taRepo.findById(taId)
                .orElseThrow(() -> new EntityNotFoundException("TA not found"));

        // The Set<Course> assistedCourses is already mapped in your entity
        return ta.getAssistedCourses();
    }




}

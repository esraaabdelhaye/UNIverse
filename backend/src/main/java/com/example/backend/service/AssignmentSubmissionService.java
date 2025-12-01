package com.example.backend.service;

import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Course;
import com.example.backend.enums.SubmissionStatus;
import com.example.backend.repository.AssignmentSubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepo assignmentRepo;

    @Autowired
    private TeachingAssistantService taService;

    // Simplified Service Method
    public List<AssignmentSubmission> getGradingQueue(long taId) {
        // 1. Get Course IDs for the TA
        Set<Long> courseIds = taService.getAssistedCourses(taId).stream()
                .map(Course::getId)
                .collect(Collectors.toSet());

        // 2. Find submissions related to those courses that need grading
        // (Requires a complex query on Submission/Assignment entities)
        return assignmentRepo.findSubmissionsAwaitingGradingForCourses(
                courseIds, SubmissionStatus.NEEDS_GRADING
        );
    }
}

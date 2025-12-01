package com.example.backend.service;

import com.example.backend.entity.Course;
import com.example.backend.entity.Question;
import com.example.backend.enums.QuestionStatus; // This is crucial
import com.example.backend.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private TeachingAssistantService taService; // Assuming this is TAService

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> getPendingQuestionsForTa(long taId) {
        // 1. Get Course IDs (from TAService)
        Set<Long> courseIds = taService.getAssistedCourses(taId).stream()
                .map(Course::getId)
                .collect(Collectors.toSet());

        // 2. Query Questions (QuestionService's responsibility)

        // FIX 1: Change List type to List<QuestionStatus> and use the enum values
        List<QuestionStatus> pendingStatuses = List.of(
                QuestionStatus.UNANSWERED,
                QuestionStatus.INPROGRESS // Use IN_PROGRESS as well, if defined
        );

        // The repository method signature must match this call:
        // findByCourseIdInAndStatusInOrderByAskedAtAsc(Set<Long>, List<QuestionStatus>)
        return questionRepo.findByCourseIdInAndStatusInOrderByAskedAtAsc(
                courseIds,
                pendingStatuses
        );
    }
}
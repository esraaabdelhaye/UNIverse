package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Course;
import com.example.backend.entity.Question;
import com.example.backend.service.AssignmentSubmissionService;
import com.example.backend.service.QuestionService;
import com.example.backend.service.TeachingAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ta")
public class TeachingAssistantController {

    @Autowired
    private TeachingAssistantService taService;

    @Autowired
    private QuestionService questionService; // Inject the service that has the filtering logic

    @Autowired
    private AssignmentSubmissionService submissionService; // Inject the service for grading queue

    // Endpoint for Course Management (TA's core responsibility)
    @GetMapping("/{taId}/courses")
    public ResponseEntity<ApiResponse<Set<Course>>> getTaCourses(@PathVariable long taId) {
        // Core TA relationship logic belongs here
        Set<Course> courses = taService.getAssistedCourses(taId);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    // Endpoint for Student Queries
    @GetMapping("/{taId}/questions/pending")
    public ResponseEntity<ApiResponse<List<Question>>> getPendingQuestions(@PathVariable long taId) {
        // Delegation: Call the service that knows how to query Questions
        List<Question> questions = questionService.getPendingQuestionsForTa(taId);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    // Endpoint for Grading Queue
    @GetMapping("/{taId}/grading-queue")
    public ResponseEntity<ApiResponse<List<AssignmentSubmission>>> getGradingQueue(@PathVariable long taId) {
        // Delegation: Call the service that knows how to query Submissions
        List<AssignmentSubmission> submissions = submissionService.getGradingQueue(taId);
        return ResponseEntity.ok(ApiResponse.success(submissions));
    }
}


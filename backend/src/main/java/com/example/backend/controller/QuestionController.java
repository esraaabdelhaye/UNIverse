package com.example.backend.controller;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.QuestionDTO;
import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.request.AnswerQuestionRequest;
import com.example.backend.dto.request.AskQuestionRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/ask")
    public ResponseEntity<ApiResponse<QuestionDTO>> askQuestion(
            @AuthenticationPrincipal StudentDTO studentDTO,
            @RequestBody AskQuestionRequest request) {
        ApiResponse<QuestionDTO> response = questionService.askQuestion(studentDTO, request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<ApiResponse<QuestionDTO>> answerQuestion(
            @AuthenticationPrincipal DoctorDTO doctorDTO,
            @RequestBody AnswerQuestionRequest request) {
        ApiResponse<QuestionDTO> response = questionService.answerQuestion(doctorDTO, request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/update/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> updateQuestion(
            @AuthenticationPrincipal StudentDTO studentDTO,
            @PathVariable String questionId,
            @RequestBody QuestionDTO questionDTO) {
        questionDTO.setQuestionId(questionId);
        ApiResponse<QuestionDTO> response = questionService.updateQuestion(studentDTO, questionDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/addUpVote/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> addUpvote(
            @PathVariable String questionId) {
        ApiResponse<QuestionDTO> response = questionService.addUpvote(questionId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/incrementViewUpCount/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> incrementViewCount(
            @PathVariable String questionId) {
        ApiResponse<QuestionDTO> response = questionService.incrementViewCount(questionId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> deleteQuestion(
            @AuthenticationPrincipal StudentDTO studentDTO,
            @PathVariable String questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(questionId);
        ApiResponse<QuestionDTO> response = questionService.deleteQuestion(studentDTO, questionDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Question retrieval endPoints
     */
    @GetMapping("/get/{questionId}")
    public ResponseEntity<ApiResponse<QuestionDTO>> getQuestion(
            @PathVariable String questionId) {
        ApiResponse<QuestionDTO> response = questionService.getQuestion(questionId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getAllQuestions(
            @RequestParam int page,
            @RequestParam int pageSize) {
        ApiResponse<List<QuestionDTO>> response = questionService.getAllQuestions(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all/{studentId}")
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getQuestionsByStudent(
            @PathVariable String studentId,
            @RequestParam int page,
            @RequestParam int pageSize) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentId);
        ApiResponse<List<QuestionDTO>> response = questionService.getQuestionsByStudent(studentDTO, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all/status")
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getQuestionsByStatus(
            @RequestParam String status) {
        ApiResponse<List<QuestionDTO>> response = questionService.getQuestionsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all/unanswered")
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getUnansweredQuestions() {
        ApiResponse<List<QuestionDTO>> response = questionService.getUnansweredQuestions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> searchQuestions(
            @RequestParam String keyword) {
        ApiResponse<List<QuestionDTO>> response = questionService.searchQuestions(keyword);
        return ResponseEntity.ok(response);
    }
}

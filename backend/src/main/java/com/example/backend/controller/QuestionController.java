package com.example.backend.controller;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.QuestionDTO;
import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.request.AnswerQuestionRequest;
import com.example.backend.dto.request.AskQuestionRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Student;
import com.example.backend.service.QuestionService;
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
    ApiResponse<QuestionDTO> askQuestion(@AuthenticationPrincipal StudentDTO studentDTO, @RequestBody AskQuestionRequest request) {
        return this.questionService.askQuestion(studentDTO, request);
    }

    @PostMapping("/answer")
    ApiResponse<QuestionDTO> answerQuestion(@AuthenticationPrincipal DoctorDTO doctorDTO, @RequestBody AnswerQuestionRequest request) {
        return this.questionService.answerQuestion(doctorDTO, request);
    }

    @PostMapping("/update/{questionId}")
    ApiResponse<QuestionDTO> updateQuestion(@AuthenticationPrincipal StudentDTO studentDTO, @PathVariable String questionId, @RequestBody QuestionDTO questionDTO) {
        questionDTO.setQuestionId(questionId);
        return this.questionService.updateQuestion(studentDTO, questionDTO);
    }

    @PostMapping("/addUpVote/{questionId}")
    ApiResponse<QuestionDTO> addUpvote(@PathVariable String questionId) {
        return this.questionService.addUpvote(questionId);
    }

    @PostMapping("/incrementViewUpCount/{questionId}")
    ApiResponse<QuestionDTO> incrementViewCount(@PathVariable String questionId) {
        return this.questionService.incrementViewCount(questionId);
    }

    @DeleteMapping("/{questionId}")
    ApiResponse<QuestionDTO> deleteQuestion(@AuthenticationPrincipal StudentDTO studentDTO, @PathVariable String questionId) {
        QuestionDTO questionDTO = new QuestionDTO() ;
        questionDTO.setQuestionId(questionId);
        return this.questionService.deleteQuestion(studentDTO,questionDTO);
    }

    /**
     * Question retrieval endPoints
     */
    @GetMapping("/get/{questionId}")
    ApiResponse<QuestionDTO> getQuestion(@PathVariable String questionId) {
        return this.questionService.getQuestion(questionId);
    }

    @GetMapping("/get/all")
    ApiResponse<List<QuestionDTO>> getAllQuestions(@RequestParam int page, @RequestParam int pageSize) {
        return this.questionService.getAllQuestions(page,pageSize);
    }

    @GetMapping("/get/all/{studentId}")
    ApiResponse<List<QuestionDTO>> getQuestionsByStudent(@PathVariable String studentId,@RequestParam int page, @RequestParam int pageSize) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentId);
        return this.questionService.getQuestionsByStudent(studentDTO,page,pageSize);
    }

    @GetMapping("/get/all/status")
    ApiResponse<List<QuestionDTO>> getQuestionsByStatus(@RequestParam String status) {
        return this.questionService.getQuestionsByStatus(status);
    }

    @GetMapping("/get/all/unanswered")
    ApiResponse<List<QuestionDTO>> getUnansweredQuestions() {
        return this.questionService.getUnansweredQuestions();
    }

    @GetMapping("/search")
    ApiResponse<List<QuestionDTO>> searchQuestions(@RequestParam String keyword) {
        return this.questionService.searchQuestions(keyword);
    }



}

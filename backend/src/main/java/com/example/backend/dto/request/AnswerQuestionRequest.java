package com.example.backend.dto.request;

public class AnswerQuestionRequest {
    private String questionId;
    private String answerContent;

    public AnswerQuestionRequest() {}

    public AnswerQuestionRequest(String questionId, String answerContent) {
        this.questionId = questionId;
        this.answerContent = answerContent;
    }

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getAnswerContent() { return answerContent; }
    public void setAnswerContent(String answerContent) { this.answerContent = answerContent; }
}
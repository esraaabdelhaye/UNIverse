package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class AskQuestionRequest {

    private String courseCode;
    private String questionTitle;
    private String questionContent;
    private String[] tags;
    private LocalDateTime askedDate;
    private Integer answerCount;
    private String status;
    private String priority;

    public AskQuestionRequest() {}

    public AskQuestionRequest(String courseCode, String questionTitle, String questionContent, String[] tags) {
        this.courseCode = courseCode;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.tags = tags;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getQuestionTitle() { return questionTitle; }
    public void setQuestionTitle(String questionTitle) { this.questionTitle = questionTitle; }

    public String getQuestionContent() { return questionContent; }
    public void setQuestionContent(String questionContent) { this.questionContent = questionContent; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public LocalDateTime getAskedDate() { return askedDate; }
    public void setAskedDate(LocalDateTime askedDate) { this.askedDate = askedDate; }

    public Integer getAnswerCount() { return answerCount; }
    public void setAnswerCount(Integer answerCount) { this.answerCount = answerCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}

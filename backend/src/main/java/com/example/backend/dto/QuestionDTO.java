package com.example.backend.dto;

import java.time.LocalDateTime;

public class QuestionDTO {
    private String questionId;
    private String courseCode;
    private String askedBy;
    private String askerName;
    private String questionTitle;
    private String questionContent;
    private LocalDateTime askedDate;
    private Integer answerCount;
    private Integer viewCount;
    private String status;
    private String priority;
    private String[] tags;
    private Integer upvotes;

    public QuestionDTO() {}

    public QuestionDTO(String questionId, String courseCode, String askedBy, String askerName,
                       String questionTitle, String questionContent, LocalDateTime askedDate,
                       Integer answerCount, Integer viewCount, String status, String priority,
                       String[] tags, Integer upvotes) {
        this.questionId = questionId;
        this.courseCode = courseCode;
        this.askedBy = askedBy;
        this.askerName = askerName;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.askedDate = askedDate;
        this.answerCount = answerCount;
        this.viewCount = viewCount;
        this.status = status;
        this.priority = priority;
        this.tags = tags;
        this.upvotes = upvotes;
    }

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getAskedBy() { return askedBy; }
    public void setAskedBy(String askedBy) { this.askedBy = askedBy; }

    public String getAskerName() { return askerName; }
    public void setAskerName(String askerName) { this.askerName = askerName; }

    public String getQuestionTitle() { return questionTitle; }
    public void setQuestionTitle(String questionTitle) { this.questionTitle = questionTitle; }

    public String getQuestionContent() { return questionContent; }
    public void setQuestionContent(String questionContent) { this.questionContent = questionContent; }

    public LocalDateTime getAskedDate() { return askedDate; }
    public void setAskedDate(LocalDateTime askedDate) { this.askedDate = askedDate; }

    public Integer getAnswerCount() { return answerCount; }
    public void setAnswerCount(Integer answerCount) { this.answerCount = answerCount; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public Integer getUpvotes() { return upvotes; }
    public void setUpvotes(Integer upvotes) { this.upvotes = upvotes; }
}

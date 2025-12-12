package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "answer" , nullable = true)
    private String answer;

    @Column(name = "answered_at", nullable = true)
    private LocalDateTime answeredAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "priority", nullable = true)
    private String priority;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "upvotes", nullable = false)
    private Integer upvotes = 0;

    @Column(name = "answer_count", nullable = false)
    private Integer answerCount = 0;

    @Column(name = "tags", nullable = true)
    private String tags; // Comma-separated list of tags


    // UniDirectional relation for now
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taResponder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Doctor doctorResponder;

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Student getAuthor() {
        return author;
    }

    public void setAuthor(Student author) {
        this.author = author;
    }

    public TeachingAssistant getTaResponder() {
        return taResponder;
    }

    public void setTaResponder(TeachingAssistant taResponder) {
        this.taResponder = taResponder;
    }

    public Doctor getDoctorResponder() {
        return doctorResponder;
    }

    public void setDoctorResponder(Doctor doctorResponder) {
        this.doctorResponder = doctorResponder;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }
    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public Integer getViewCount() {
        return viewCount;
    }
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    public Integer getUpvotes() {
        return upvotes;
    }
    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }
    public Integer getAnswerCount() {
        return answerCount;
    }
    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    
}

package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "responder_id", nullable = false)
    private User responder;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime answeredAt = LocalDateTime.now();
}


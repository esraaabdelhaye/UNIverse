package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private String assignmentId;
    private String courseCode;
    private String assignmentTitle;
    private LocalDateTime dueDate;
    private String status;
    private Double grade;
    private String feedbackUrl;
}

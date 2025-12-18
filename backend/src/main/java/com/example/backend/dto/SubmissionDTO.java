package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {
    private int assignmentId;          // to filter submissions based on assignments
    private String submissionId;
    private String studentId;
    private String studentName;
    private String assignmentTitle;

    private LocalDateTime submissionDate;
    private String status;
    private Double grade;

}

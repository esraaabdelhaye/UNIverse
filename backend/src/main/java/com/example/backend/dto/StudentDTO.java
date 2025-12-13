package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String studentId;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String role;
    private String phoneNumber;
    private String enrollmentStatus;

}


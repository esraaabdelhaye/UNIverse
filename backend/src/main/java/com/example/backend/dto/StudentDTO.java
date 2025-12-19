package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String studentId;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String enrollmentStatus;
    private String role ;

}


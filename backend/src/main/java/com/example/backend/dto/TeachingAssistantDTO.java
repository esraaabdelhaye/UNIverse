package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeachingAssistantDTO {
    private String employeeId;
    private String fullName;
    private String email;
    private String role;
    private String phoneNumber;
    private String department;
}

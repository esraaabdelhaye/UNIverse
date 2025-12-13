package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO implements  PostAuthor, AnnouncementAuthor, PollAuthor {
    private String doctorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String department;
    private String officeLocation;
    private String qualifications;
    private Boolean availableForConsultation;
    private LocalDate startDate;
    private String role;
}

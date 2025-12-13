package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDTO implements PostAuthor , AnnouncementAuthor, PollAuthor {
    private String employeeId;
    private String fullName;
    private String email;
    private String department;
    private String positionTitle;
    private String officeLocation;
    private String role;
}

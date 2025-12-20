package com.example.backend.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDTO implements PostAuthor , AnnouncementAuthor, PollAuthor,EventAuthor, Serializable {
    private String employeeId;
    private String fullName;
    private String email;
    private String department;
    private String positionTitle;
    private String officeLocation;
    private String role;
}

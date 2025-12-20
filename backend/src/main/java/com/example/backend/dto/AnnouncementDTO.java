package com.example.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnnouncementDTO {
    private String announcementId;
    private String courseCode;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdDate;
    private String status;
    private String visibility;
}


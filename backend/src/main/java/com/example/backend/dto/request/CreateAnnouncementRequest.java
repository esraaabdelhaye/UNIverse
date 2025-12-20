package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnnouncementRequest {
    private String courseCode;
    private String title;
    private String content;
    private LocalDateTime publishDate;
    private String visibility;
    private String[] attachments;

}
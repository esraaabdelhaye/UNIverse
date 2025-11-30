package com.example.backend.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private String notificationId;
    private String userId;
    private String title;
    private String message;
    private String notificationType;
    private String priority;
    private Boolean isRead;
    private LocalDateTime createdDate;
    private LocalDateTime readDate;
    private String relatedEntityId;
    private String relatedEntityType;

    public NotificationDTO() {}

    public NotificationDTO(String notificationId, String userId, String title, String message,
                           String notificationType, String priority, Boolean isRead, LocalDateTime createdDate,
                           LocalDateTime readDate, String relatedEntityId, String relatedEntityType) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.priority = priority;
        this.isRead = isRead;
        this.createdDate = createdDate;
        this.readDate = readDate;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }

    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getReadDate() { return readDate; }
    public void setReadDate(LocalDateTime readDate) { this.readDate = readDate; }

    public String getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(String relatedEntityId) { this.relatedEntityId = relatedEntityId; }

    public String getRelatedEntityType() { return relatedEntityType; }
    public void setRelatedEntityType(String relatedEntityType) { this.relatedEntityType = relatedEntityType; }
}

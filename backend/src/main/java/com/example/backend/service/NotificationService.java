package com.example.backend.service;

import com.example.backend.Utils.NotificationType;
import com.example.backend.dto.NotificationDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Notification;
import com.example.backend.entity.Student;
import com.example.backend.repository.NotificationRepo;
import com.example.backend.repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepo notificationRepo ;
    private final StudentRepo studentRepo ;

    public NotificationService(NotificationRepo notificationRepo, StudentRepo studentRepo) {
        this.notificationRepo = notificationRepo;
        this.studentRepo = studentRepo;
    }

    /**
     * Method to notify students
     * Later we will overload it to notify different types of users
     */
    public void notify(Long studentId , NotificationDTO notificationDTO) {
        try {
            Optional<Student> studentOpt = studentRepo.findById(studentId);
            if (studentOpt.isEmpty()) throw new RuntimeException("Student not found when creating notification") ;
            Notification notification = new Notification() ;
            notification.setMessage(notificationDTO.getMessage());
            notification.setTitle(notificationDTO.getTitle());
            notification.setType(typeToEnum(notificationDTO.getNotificationType()));
            notification.setRecipient(studentOpt.get());
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepo.save(notification);
        }
        catch (Exception e) {
            throw new RuntimeException("Error in saving notification : "+e) ;
        }
    }

    public List<NotificationDTO> getUserNotifications(Long studentId) {
        return notificationRepo.findByStudentId(studentId).stream().
                map(this::notificationToDTO).
                collect(Collectors.toList());
    }

    /**
     * Currently the notification stores a list of student
     * which will cause errors with mark as read so either
     * each time we notify student we create new notification
     * while keeping it as list such that we don't break other code
     * or modify it and find each place depending on the list and modify it
     */
    /*
        I have modified it to use one student instead of list
        Currently it is safe
     */
    public void markAsRead(Long notificationId , Long studentId) {
        Notification n = notificationRepo.findByIdAndStudentId(notificationId , studentId)
                .orElseThrow();
        n.setRead(true);
        notificationRepo.save(n);
    }

    private NotificationType typeToEnum(String type) {
        return NotificationType.valueOf(type.toUpperCase());
    }

    private NotificationDTO notificationToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO() ;
        notificationDTO.setTitle(notification.getTitle());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setNotificationId(String.valueOf(notification.getId()));
        notificationDTO.setCreatedDate(notification.getCreatedAt());
        notificationDTO.setNotificationType(notification.getType().toString());
        notificationDTO.setUserId(String.valueOf(notification.getRecipient().getId()));
        notificationDTO.setIsRead(notification.isRead());
        return notificationDTO;
    }
}

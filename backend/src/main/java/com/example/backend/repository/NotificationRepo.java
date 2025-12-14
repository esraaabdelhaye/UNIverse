package com.example.backend.repository;

import com.example.backend.dto.NotificationDTO;
import com.example.backend.entity.Notification;
import com.example.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepo extends JpaRepository<Notification,Long> {

    List<Notification> findByStudentId(Long id);

    Optional<Notification> findByIdAndStudentId(Long id, Long student_id);
}

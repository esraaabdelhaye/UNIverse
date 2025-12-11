package com.example.backend.repository;

import com.example.backend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    List<Announcement> findByDoctorAuthor_Id(Long doctorId);

    List<Announcement> findBySupervisorAuthor_Id(Long supervisorId);

    List<Announcement> findByStatus(String status);

    List<Announcement> findByCourse_CourseCode(String courseCode);

    List<Announcement> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}

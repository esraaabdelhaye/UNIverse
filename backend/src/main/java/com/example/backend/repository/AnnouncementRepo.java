package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Announcement;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    List<Announcement> findByDoctorAuthor_Id(Long doctorId);

    List<Announcement> findBySupervisorAuthor_Id(Long supervisorId);

    List<Announcement> findByStatus(String status);

    List<Announcement> findByCourse_CourseCode(String courseCode);

    List<Announcement> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    
    List<Announcement> findByCourseId(Long courseId);
    
    @Modifying
    @Query("DELETE FROM Announcement a WHERE a.course.id = :courseId")
    void deleteAllByCourseId(@Param("courseId") Long courseId);
}

package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Schedule;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findBySemester(String semester);
    List<Schedule> findBySemesterAndAcademicYear(String semester, String academicYear);
    void deleteBySemester(String semester);
    void deleteBySemesterAndAcademicYear(String semester, String academicYear);
    
    // Methods for course deletion
    List<Schedule> findByCourseId(Long courseId);
    
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.course.id = :courseId")
    void deleteAllByCourseId(@Param("courseId") Long courseId);
}

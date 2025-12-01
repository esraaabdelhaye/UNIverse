package com.example.backend.repository;

import com.example.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    List<Course> findByDepartmentId(Long departmentId);

    List<Course> findBySemester(String semester);

    List<Course> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM Course c WHERE c.department.id = :departmentId AND c.semester = :semester")
    List<Course> findByDepartmentAndSemester(@Param("departmentId") Long departmentId, @Param("semester") String semester);
}
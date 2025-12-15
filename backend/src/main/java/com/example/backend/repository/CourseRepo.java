package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    List<Course> findByDepartmentId(Long departmentId);

    List<Course> findBySemester(String semester);

    List<Course> findByNameContainingIgnoreCase(String name);

    List<Course> findByNameContainingIgnoreCaseOrCourseCodeContainingIgnoreCase(String name, String courseCode);

    @Query("SELECT c FROM Course c WHERE c.department.id = :departmentId AND c.semester = :semester")
    List<Course> findByDepartmentAndSemester(@Param("departmentId") Long departmentId, @Param("semester") String semester);
    
    // Native SQL methods to delete junction table entries
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM doctor_course WHERE course_id = :courseId", nativeQuery = true)
    void deleteDoctorCourseByCourseId(@Param("courseId") Long courseId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM teaching_assistant_course WHERE course_id = :courseId", nativeQuery = true)
    void deleteTaCourseByCourseId(@Param("courseId") Long courseId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM supervisor_course WHERE course_id = :courseId", nativeQuery = true)
    void deleteCoordinatorCourseByCourseId(@Param("courseId") Long courseId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_prerequisite WHERE course_id = :courseId OR prerequisite_id = :courseId", nativeQuery = true)
    void deleteCoursePrerequisitesByCourseId(@Param("courseId") Long courseId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM semester_courses WHERE course_id = :courseId", nativeQuery = true)
    void deleteSemesterCoursesByCourseId(@Param("courseId") Long courseId);
}
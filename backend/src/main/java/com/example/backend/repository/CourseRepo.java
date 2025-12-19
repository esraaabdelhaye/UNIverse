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

    // ===== JOIN FETCH methods to avoid LazyInitializationException =====
    
    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.doctors WHERE c.id = :id")
    Optional<Course> findByIdWithDoctors(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.doctors")
    List<Course> findAllWithDoctors();

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.doctors WHERE c.courseCode = :courseCode")
    Optional<Course> findByCourseCodeWithDoctors(@Param("courseCode") String courseCode);

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.doctors WHERE c.department.id = :departmentId")
    List<Course> findByDepartmentIdWithDoctors(@Param("departmentId") Long departmentId);

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.doctors WHERE c.semester = :semester")
    List<Course> findBySemesterWithDoctors(@Param("semester") String semester);
}
package com.example.backend.repository;

import com.example.backend.entity.CourseEnrollment;
import com.example.backend.entity.Student;
import com.example.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface CourseEnrollmentRepo extends JpaRepository<CourseEnrollment, Long> {

    boolean existsByStudentAndCourse(Student student, Course course);

    Optional<CourseEnrollment> findByStudentAndCourse(Student student, Course course);

    List<CourseEnrollment> findByStudent(Student student);

    List<CourseEnrollment> findByCourse(Course course);

    long countByCourse(Course course);
}
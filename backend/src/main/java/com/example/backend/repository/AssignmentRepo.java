package com.example.backend.repository;

import com.example.backend.entity.Assignment;
import com.example.backend.entity.Course;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findByTitle(String title);

    List<Assignment> findByCourse(Course course);

    boolean existsByTitleAndCourse(String title, Course course);

    List<Assignment> findByOrderByDueDateAsc();

    List<Assignment> findByCourseOrderByDueDateAsc(Course course);

    JsonNode findByCourseAndTitle(Course course, String title);
}
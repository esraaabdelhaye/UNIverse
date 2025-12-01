package com.example.backend.repository;

import com.example.backend.dto.CourseDTO;
import com.example.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Integer> {

    List<CourseDTO> getCourseByInstructorID(String instructorID);
    // TODO: Implement repository logic (JPA/Hibernate/etc.)

}

package com.example.backend.repository;

import com.example.backend.entity.Material;
import com.example.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialRepo extends JpaRepository<Material, Long> {

    List<Material> findByCourse(Course course);

    List<Material> findByCourseId(Long courseId);

    List<Material> findByCourseAndType(Course course, String type);
}

package com.example.backend.repository;

import com.example.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByAcademicId(Long academicId);

    List<Student> findByDepartmentId(Long departmentId);

    boolean existsByEmail(String email);

    boolean existsByAcademicId(Long academicId);
}
package com.example.backend.repository;

import com.example.backend.entity.StudentRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepresentativeRepo extends JpaRepository<StudentRepresentative, Long> {

    // TODO: Implement repository logic (JPA/Hibernate/etc.)

    Optional<StudentRepresentative> findByEmail(String email);
    Optional<StudentRepresentative> findByAcademicId(Long id);
    Optional<StudentRepresentative> findByDepartmentId(Long departmentId);
}

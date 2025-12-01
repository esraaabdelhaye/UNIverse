package com.example.backend.repository;

import com.example.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {

    // TODO: Implement repository logic (JPA/Hibernate/etc.)
    Optional<Department> findByName(String name);

}

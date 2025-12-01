package com.example.backend.repository;

import com.example.backend.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupRepo extends JpaRepository<StudentGroup,Long> {
}

package com.example.backend.repository;

import com.example.backend.entity.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepo extends JpaRepository<CourseEnrollment,Integer> {
}

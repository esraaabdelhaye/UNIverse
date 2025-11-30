package com.example.backend.repository;

import com.example.backend.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission,Integer> {
}

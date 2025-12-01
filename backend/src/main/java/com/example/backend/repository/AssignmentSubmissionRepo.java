package com.example.backend.repository;

import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.entity.Student;
import com.example.backend.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission, Long> {

    List<AssignmentSubmission> findByStudent(Student student);

    List<AssignmentSubmission> findByAssignment(Assignment assignment);

    Optional<AssignmentSubmission> findByStudentAndAssignment(Student student, Assignment assignment);

    List<AssignmentSubmission> findByStatus(String status);

    long countByAssignment(Assignment assignment);

    long countByStudentAndGradeIsNotNull(Student student);
}
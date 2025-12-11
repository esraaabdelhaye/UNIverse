package com.example.backend.repository;

import com.example.backend.entity.Submission;
import com.example.backend.entity.Student;
import com.example.backend.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission, Long> {

    List<Submission> findByStudent(Student student);

    List<Submission> findByAssignment(Assignment assignment);

    Optional<Submission> findByStudentAndAssignment(Student student, Assignment assignment);

    List<Submission> findByStatus(String status);

    List<Submission> findByStudentOrderBySubmissionDateDesc(Student student);

    long countByAssignment(Assignment assignment);

    long countByStudentAndGradeIsNotNull(Student student);
}
package com.example.backend.repository;

import com.example.backend.entity.AssignmentSubmission;
import com.example.backend.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission,Long> {

    @Query("SELECT s FROM AssignmentSubmission s WHERE " +
            // *** CORRECTED JPQL ***
            "s.course.id IN :courseIds AND " +
            "s.status = :status " +
            "ORDER BY s.submissionDate ASC")
    List<AssignmentSubmission> findSubmissionsAwaitingGradingForCourses(
            @Param("courseIds") Set<Long> courseIds,
            @Param("status") SubmissionStatus status
    );
}

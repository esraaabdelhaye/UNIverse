package com.example.backend.repository;

import com.example.backend.entity.Question;
import com.example.backend.enums.QuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuestionRepo extends JpaRepository<Question,Long> {

    // Inside QuestionRepo.java
    List<Question> findByCourseIdInAndStatusInOrderByAskedAtAsc(
            Set<Long> courseIds,
            List<QuestionStatus> statuses
    );
}

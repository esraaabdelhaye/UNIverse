package com.example.backend.repository;

import com.example.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findByAuthor_Id(Long studentId);

    List<Question> findByStatus(String status);

    List<Question> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    List<Question> findByDoctorResponder_Id(Long doctorId);
}

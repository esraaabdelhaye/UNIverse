package com.example.backend.repository;

import com.example.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByDoctors_Id(Long id);

    List<Post> findByStatus(String status);

    List<Post> findByTitleContainingOrContentContaining(String title, String content);
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

}

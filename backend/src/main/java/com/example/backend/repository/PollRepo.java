package com.example.backend.repository;

import com.example.backend.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepo extends JpaRepository<Poll,Long> {
}

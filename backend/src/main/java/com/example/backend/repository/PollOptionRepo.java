package com.example.backend.repository;

import com.example.backend.entity.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollOptionRepo extends JpaRepository<PollOption,Long> {
}

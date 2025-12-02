package com.example.backend.repository;

import com.example.backend.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SupervisorRepo extends JpaRepository<Supervisor, Long> {

    Optional<Supervisor> findByEmail(String email);

}

package com.example.backend.repository;

import com.example.backend.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepo extends JpaRepository<Material,Long> {
}

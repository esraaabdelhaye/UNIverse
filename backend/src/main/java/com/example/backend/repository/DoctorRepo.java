package com.example.backend.repository;

import com.example.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    Optional<Doctor> findByEmail(String email);


}

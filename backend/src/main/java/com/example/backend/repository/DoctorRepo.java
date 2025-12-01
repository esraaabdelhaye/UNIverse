package com.example.backend.repository;

import com.example.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.dto.DoctorDTO;

public interface DoctorRepo extends JpaRepository<Doctor,String> {
    Doctor getDoctorById(String id);

    // TODO: Implement repository logic (JPA/Hibernate/etc.)


}

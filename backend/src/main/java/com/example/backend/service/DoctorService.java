package com.example.backend.service;

import com.example.backend.repository.DoctorRepo;
import com.example.backend.dto.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for all business logic related to the Doctor entity.
 * Handles authentication, profile management, and data mapping.
 */
@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;

    @Autowired
    public DoctorService(DoctorRepo docRepo) {
        this.doctorRepo = docRepo;
    }

    public DoctorDTO getDoctorDTO(String ID) {
        return this.doctorRepo.getDoctorById(ID);
    }

   }
package com.example.backend.service;

import com.example.backend.dto.CourseDTO;
import com.example.backend.entity.Course;
import com.example.backend.entity.Doctor;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.dto.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final CourseRepo courseRepo;

    @Autowired
    public DoctorService(DoctorRepo docRepo, CourseRepo courseRepo) {
        this.doctorRepo = docRepo;
        this.courseRepo = courseRepo;
    }

    public Doctor getDoctorDTO(String ID) {
        return this.doctorRepo.getDoctorById(ID);
    }

    public Set<Course> getCourseDTOs(String ID) {
        Doctor doc =  getDoctorDTO(ID);
        return doc.getCourses();
    }

   }
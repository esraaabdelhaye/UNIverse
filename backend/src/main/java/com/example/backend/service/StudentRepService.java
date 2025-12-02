package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentRepService {

    private final StudentRepresentativeRepo studentRepRepo;
    private final StudentRepo studentRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public StudentRepService(
            StudentRepresentativeRepo studentRepRepo,
            StudentRepo studentRepo,
            DepartmentRepo departmentRepo
    ) {
        this.studentRepRepo = studentRepRepo;
        this.studentRepo = studentRepo;
        this.departmentRepo = departmentRepo;
    }

    public ApiResponse<Page<DoctorDTO>> getAllStudentReps(Pageable pageable) {
        try {
            Page<StudentRepresentative> reps = studentRepRepo.findAll(pageable);
            Page<DoctorDTO> repDTOs = reps.map(this::convertToDTO);

            return ApiResponse.success(repDTOs);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student representatives: " + e.getMessage());
        }
    }

    public ApiResponse<DoctorDTO> getStudentRepById(Long id) {
        try {
            Optional<StudentRepresentative> repOpt = studentRepRepo.findById(id);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }

            DoctorDTO dto = convertToDTO(repOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student representative: " + e.getMessage());
        }
    }

    public ApiResponse<DoctorDTO> getStudentRepByEmail(String email) {
        try {
            Optional<StudentRepresentative> repOpt = studentRepRepo.findByEmail(email);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with email: " + email);
            }

            DoctorDTO dto = convertToDTO(repOpt.get());
            return ApiResponse.success(dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching student representative: " + e.getMessage());
        }
    }

    public ApiResponse<DoctorDTO> registerStudentRep(DoctorDTO request) {
        try {
            if (studentRepo.findByEmail(request.getEmail()).isPresent()) {
                return ApiResponse.conflict("Student Representative with this email already exists");
            }

            StudentRepresentative rep = new StudentRepresentative();
            rep.setName(request.getFullName());
            rep.setEmail(request.getEmail());
            rep.setRole("Student Representative");
            rep.setStatus("Active");

            StudentRepresentative savedRep = studentRepRepo.save(rep);

            return ApiResponse.created("Student Representative registered successfully", convertToDTO(savedRep));

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error registering student representative: " + e.getMessage());
        }
    }

    public ApiResponse<DoctorDTO> updateStudentRep(Long id, DoctorDTO repDTO) {
        try {
            Optional<StudentRepresentative> repOpt = studentRepRepo.findById(id);

            if (repOpt.isEmpty()) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }

            StudentRepresentative rep = repOpt.get();

            if (repDTO.getFullName() != null) {
                rep.setName(repDTO.getFullName());
            }
            if (repDTO.getEmail() != null) {
                rep.setEmail(repDTO.getEmail());
            }

            StudentRepresentative updatedRep = studentRepRepo.save(rep);
            DoctorDTO dto = convertToDTO(updatedRep);

            return ApiResponse.success("Student Representative updated successfully", dto);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating student representative: " + e.getMessage());
        }
    }

    public ApiResponse<Void> deleteStudentRep(Long id) {
        try {
            if (!studentRepRepo.existsById(id)) {
                return ApiResponse.notFound("Student Representative not found with ID: " + id);
            }

            studentRepRepo.deleteById(id);
            return ApiResponse.success("Student Representative deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting student representative: " + e.getMessage());
        }
    }

    private DoctorDTO convertToDTO(StudentRepresentative rep) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(String.valueOf(rep.getId()));
        dto.setFullName(rep.getName());
        dto.setEmail(rep.getEmail());
        dto.setSpecialization(rep.getRole());
        dto.setAvailableForConsultation(true);
        if (rep.getDepartment() != null) {
            dto.setDepartment(rep.getDepartment().getName());
        }
        return dto;
    }
}

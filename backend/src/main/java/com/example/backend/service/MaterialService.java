package com.example.backend.service;

import com.example.backend.dto.MaterialDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Material;
import com.example.backend.entity.Course;
import com.example.backend.repository.MaterialRepo;
import com.example.backend.repository.CourseRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaterialService {

    private final MaterialRepo materialRepo;
    private final CourseRepo courseRepo;

    public MaterialService(MaterialRepo materialRepo, CourseRepo courseRepo) {
        this.materialRepo = materialRepo;
        this.courseRepo = courseRepo;
    }

    // Get all materials for a course
    public ApiResponse<List<MaterialDTO>> getMaterialsByCourse(Long courseId) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            List<MaterialDTO> materials = courseOpt.get().getMaterials().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(materials);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching materials: " + e.getMessage());
        }
    }

    // Get material by ID
    public ApiResponse<MaterialDTO> getMaterialById(Long materialId) {
        try {
            Optional<Material> materialOpt = materialRepo.findById(materialId);
            if (materialOpt.isEmpty()) {
                return ApiResponse.notFound("Material not found");
            }

            return ApiResponse.success(convertToDTO(materialOpt.get()));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching material: " + e.getMessage());
        }
    }

    // Upload material
    public ApiResponse<MaterialDTO> uploadMaterial(Long courseId, MaterialDTO materialDTO) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            Material material = new Material();
            material.setTitle(materialDTO.getMaterialTitle());
            material.setUrl(materialDTO.getDownloadUrl());
            material.setUploadDate(LocalDateTime.now());
            material.setCourse(courseOpt.get());

            Material saved = materialRepo.save(material);
            return ApiResponse.created("Material uploaded successfully", convertToDTO(saved));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error uploading material: " + e.getMessage());
        }
    }

    // Update material
    public ApiResponse<MaterialDTO> updateMaterial(Long materialId, MaterialDTO materialDTO) {
        try {
            Optional<Material> materialOpt = materialRepo.findById(materialId);
            if (materialOpt.isEmpty()) {
                return ApiResponse.notFound("Material not found");
            }

            Material material = materialOpt.get();
            if (materialDTO.getMaterialTitle() != null) {
                material.setTitle(materialDTO.getMaterialTitle());
            }
            if (materialDTO.getDownloadUrl() != null) {
                material.setUrl(materialDTO.getDownloadUrl());
            }

            Material updated = materialRepo.save(material);
            return ApiResponse.success("Material updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating material: " + e.getMessage());
        }
    }

    // Delete material
    public ApiResponse<Void> deleteMaterial(Long materialId) {
        try {
            if (!materialRepo.existsById(materialId)) {
                return ApiResponse.notFound("Material not found");
            }

            materialRepo.deleteById(materialId);
            return ApiResponse.success("Material deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error deleting material: " + e.getMessage());
        }
    }

    // Get all materials (paginated)
    public ApiResponse<List<MaterialDTO>> getAllMaterials() {
        try {
            List<MaterialDTO> materials = materialRepo.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(materials);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching materials: " + e.getMessage());
        }
    }

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setMaterialId(String.valueOf(material.getId()));
        dto.setMaterialTitle(material.getTitle());
        dto.setMaterialType(material.getType() != null ? material.getType().toString() : "");
        dto.setUploadDate(material.getUploadDate());
        dto.setDownloadUrl(material.getUrl());
        if (material.getCourse() != null) {
            dto.setCourseCode(material.getCourse().getCourseCode());
        }
        return dto;
    }
}
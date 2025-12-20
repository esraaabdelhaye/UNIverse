package com.example.backend.service;

import com.example.backend.Utils.MaterialType;
import com.example.backend.dto.MaterialDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Material;
import com.example.backend.entity.Course;
import com.example.backend.repository.MaterialRepo;
import com.example.backend.repository.CourseRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaterialService {

    private final MaterialRepo materialRepo;
    private final CourseRepo courseRepo;
    private final FileStorageService fileStorageService;

    public MaterialService(MaterialRepo materialRepo, CourseRepo courseRepo, FileStorageService fileStorageService) {
        this.materialRepo = materialRepo;
        this.courseRepo = courseRepo;
        this.fileStorageService = fileStorageService;
    }

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

    public ApiResponse<List<MaterialDTO>> getMaterialsByCourse(Long courseId) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            List<MaterialDTO> materials = materialRepo.findByCourse(courseOpt.get()).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success(materials);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error fetching materials: " + e.getMessage());
        }
    }

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

    public ApiResponse<MaterialDTO> uploadMaterial(Long courseId, MultipartFile file,
                                                   String title,
                                                   MaterialType type,
                                                   String description) {
        try {
            Optional<Course> courseOpt = courseRepo.findById(courseId);
            if (courseOpt.isEmpty()) {
                return ApiResponse.notFound("Course not found");
            }

            String filePath = fileStorageService.storeMaterial(file, courseId);

            Material material = new Material();
            material.setTitle(title);
            material.setUrl(filePath);
            material.setUploadDate(LocalDateTime.now());
            material.setCourse(courseOpt.get());
            material.setDescription(description);
            material.setFileSize(file.getSize());
            material.setType(type);
            material.setFileName(file.getOriginalFilename());

            // Set icon and color based on material type
            setIconAndColor(material, type);

            Material saved = materialRepo.save(material);
            return ApiResponse.created("Material uploaded successfully", convertToDTO(saved));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error uploading material: " + e.getMessage());
        }
    }

    public ApiResponse<MaterialDTO> updateMaterial(Long materialId, MaterialDTO materialDTO) {
        try {
            Optional<Material> materialOpt = materialRepo.findById(materialId);
            if (materialOpt.isEmpty()) {
                return ApiResponse.notFound("Material not found");
            }

            Material material = materialOpt.get();
            if (materialDTO.getTitle() != null) {
                material.setTitle(materialDTO.getTitle());
            }
            if (materialDTO.getUrl() != null) {
                material.setUrl(materialDTO.getUrl());
            }
            if (materialDTO.getFormattedFileSize() != null) {
                material.setFileSize(parseFileSize(materialDTO.getFormattedFileSize()));
            }

            // Update icon and color if type changed
            if (materialDTO.getType() != null) {
                setIconAndColor(material, materialDTO.getType());
            }

            Material updated = materialRepo.save(material);
            return ApiResponse.success("Material updated successfully", convertToDTO(updated));
        } catch (Exception e) {
            return ApiResponse.internalServerError("Error updating material: " + e.getMessage());
        }
    }

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

    private void setIconAndColor(Material material, MaterialType materialType) {
        if (materialType == null || material.getType() == null) {
            material.setIconName("description");
            material.setIconColor("green-icon");
            return;
        }

        switch (material.getType()) {
            case PDF:
                material.setIconName("picture_as_pdf");
                material.setIconColor("primary-icon");
                break;
            case VIDEO:
                material.setIconName("play_circle");
                material.setIconColor("red-icon");
                break;
            case RECORDING:
                material.setIconName("mic");
                material.setIconColor("amber-icon");
                break;
            case TEXTBOOK:
                material.setIconName("menu_book");
                material.setIconColor("primary-icon");
                break;
            case OTHER:
            default:
                material.setIconName("description");
                material.setIconColor("green-icon");
                break;
        }
    }

    private Long parseFileSize(String formattedSize) {
        try {
            if (formattedSize == null || formattedSize.isEmpty()) {
                return 0L;
            }

            String[] parts = formattedSize.trim().split("\\s+");
            if (parts.length < 2) {
                return 0L;
            }

            double value = Double.parseDouble(parts[0]);
            String unit = parts[1].toUpperCase();

            long multiplier = switch (unit.charAt(0)) {
                case 'B' -> 1L;
                case 'K' -> 1024L;
                case 'M' -> 1024L * 1024L;
                case 'G' -> 1024L * 1024L * 1024L;
                case 'T' -> 1024L * 1024L * 1024L * 1024L;
                default -> 1L;
            };

            return (long) (value * multiplier);
        } catch (Exception e) {
            return 0L;
        }
    }

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setMaterialId(String.valueOf(material.getId()));
        dto.setTitle(material.getTitle());
        dto.setType(material.getType());
        dto.setUploadDate(material.getUploadDate());
        dto.setUrl(material.getUrl());
        dto.setIconName(material.getIconName());
        dto.setIconColor(material.getIconColor());
        dto.setFileName(material.getFileName());
        dto.setDescription(material.getDescription());

        // Set formatted file size
        dto.setFormattedFileSize(formatFileSize(material.getFileSize()));

        if (material.getCourse() != null) {
            dto.setCourseCode(material.getCourse().getCourseCode());
            dto.setCourseName(material.getCourse().getName());
        }

        return dto;
    }

    private String formatFileSize(Long bytes) {
        if (bytes == null || bytes == 0) return "0 B";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int unitIndex = (int) (Math.log10(bytes) / Math.log10(1024));
        double displaySize = bytes / Math.pow(1024, unitIndex);

        return String.format("%.1f %s", displaySize, units[unitIndex]);
    }
}
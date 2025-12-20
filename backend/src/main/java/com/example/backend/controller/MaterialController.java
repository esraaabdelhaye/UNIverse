package com.example.backend.controller;

import com.example.backend.Utils.MaterialType;
import com.example.backend.dto.MaterialDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<MaterialDTO>> getMaterialById(@PathVariable Long id) {
        ApiResponse<MaterialDTO> response = materialService.getMaterialById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<MaterialDTO>>> getMaterialsByCourse(
            @PathVariable Long courseId) {
        ApiResponse<List<MaterialDTO>> response = materialService.getMaterialsByCourse(courseId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<List<MaterialDTO>>> getAllMaterials() {
        ApiResponse<List<MaterialDTO>> response = materialService.getAllMaterials();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<MaterialDTO>> uploadMaterial(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("type") MaterialType type,
            @RequestParam(value = "description", required = false) String description
    ) {
        ApiResponse<MaterialDTO> response = materialService.uploadMaterial(courseId, file, title, type, description);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<MaterialDTO>> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody MaterialDTO materialDTO) {
        ApiResponse<MaterialDTO> response = materialService.updateMaterial(id, materialDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOC', 'SUPERVISOR')")
    public ResponseEntity<ApiResponse<Void>> deleteMaterial(@PathVariable Long id) {
        ApiResponse<Void> response = materialService.deleteMaterial(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
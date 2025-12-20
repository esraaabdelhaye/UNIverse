package com.example.backend.controller;

import com.example.backend.dto.FileUploadResponse;
import com.example.backend.service.FileStorageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for file upload and download operations.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload a single file to a specific category.
     */
    @PostMapping("/upload/{category}")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @PathVariable String category,
            @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileStorageService.storeFile(file, category);
            String contentType = fileStorageService.getContentType(file.getOriginalFilename());
            
            FileUploadResponse response = FileUploadResponse.success(
                    file.getOriginalFilename(),
                    filePath,
                    contentType,
                    file.getSize()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.error("Failed to upload file: " + e.getMessage()));
        }
    }

    /**
     * Upload a submission file for a student assignment.
     */
    @PostMapping("/upload/submission")
    public ResponseEntity<FileUploadResponse> uploadSubmission(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") Long studentId,
            @RequestParam("assignmentId") Long assignmentId) {
        try {
            String filePath = fileStorageService.storeSubmission(file, studentId, assignmentId);
            String contentType = fileStorageService.getContentType(file.getOriginalFilename());
            
            FileUploadResponse response = FileUploadResponse.success(
                    file.getOriginalFilename(),
                    filePath,
                    contentType,
                    file.getSize()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.error("Failed to upload submission: " + e.getMessage()));
        }
    }

    /**
     * Upload a course material.
     */
    @PostMapping("/upload/material")
    public ResponseEntity<FileUploadResponse> uploadMaterial(
            @RequestParam("file") MultipartFile file,
            @RequestParam("courseId") Long courseId) {
        try {
            String filePath = fileStorageService.storeMaterial(file, courseId);
            String contentType = fileStorageService.getContentType(file.getOriginalFilename());
            
            FileUploadResponse response = FileUploadResponse.success(
                    file.getOriginalFilename(),
                    filePath,
                    contentType,
                    file.getSize()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.error("Failed to upload material: " + e.getMessage()));
        }
    }

    /**
     * Upload a post attachment.
     */
    @PostMapping("/upload/attachment")
    public ResponseEntity<FileUploadResponse> uploadAttachment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("postId") Long postId) {
        try {
            String filePath = fileStorageService.storeAttachment(file, postId);
            String contentType = fileStorageService.getContentType(file.getOriginalFilename());
            
            FileUploadResponse response = FileUploadResponse.success(
                    file.getOriginalFilename(),
                    filePath,
                    contentType,
                    file.getSize()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.error("Failed to upload attachment: " + e.getMessage()));
        }
    }

    /**
     * Upload multiple files to a category.
     */
    @PostMapping("/upload/multiple/{category}")
    public ResponseEntity<List<FileUploadResponse>> uploadMultipleFiles(
            @PathVariable String category,
            @RequestParam("files") MultipartFile[] files) {
        List<FileUploadResponse> responses = new ArrayList<>();
        
        for (MultipartFile file : files) {
            try {
                String filePath = fileStorageService.storeFile(file, category);
                String contentType = fileStorageService.getContentType(file.getOriginalFilename());
                
                responses.add(FileUploadResponse.success(
                        file.getOriginalFilename(),
                        filePath,
                        contentType,
                        file.getSize()
                ));
            } catch (Exception e) {
                responses.add(FileUploadResponse.error("Failed to upload " + file.getOriginalFilename() + ": " + e.getMessage()));
            }
        }
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Download a file by its path.
     * Path format: category/filename (e.g., submissions/file.pdf)
     */
    @GetMapping("/download/**")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String filePath) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(filePath);
            String contentType = fileStorageService.getContentType(filePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Download a resource file from classpath (e.g., from src/main/resources)
     * Path format: Materials/ML/filename.pdf
     */
    @GetMapping("/resources/**")
    public ResponseEntity<Resource> downloadResource(@RequestParam("path") String resourcePath) {
        try {
            // Remove leading slash if present
            String cleanPath = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
            
            Resource resource = new ClassPathResource(cleanPath);
            
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            
            String contentType = fileStorageService.getContentType(resourcePath);
            String filename = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * View/preview a file inline (for PDFs, images, etc.)
     */
    @GetMapping("/view")
    public ResponseEntity<Resource> viewFile(@RequestParam("path") String filePath) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(filePath);
            String contentType = fileStorageService.getContentType(filePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * View/preview a resource file inline (for PDFs, images, etc. from classpath)
     */
    @GetMapping("/view-resource")
    public ResponseEntity<Resource> viewResource(@RequestParam("path") String resourcePath) {
        try {
            // Remove leading slash if present
            String cleanPath = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
            
            Resource resource = new ClassPathResource(cleanPath);
            
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            
            String contentType = fileStorageService.getContentType(resourcePath);
            String filename = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a file.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("path") String filePath) {
        try {
            boolean deleted = fileStorageService.deleteFile(filePath);
            if (deleted) {
                return ResponseEntity.ok("File deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete file: " + e.getMessage());
        }
    }

    /**
     * Check if a file exists.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> fileExists(@RequestParam("path") String filePath) {
        return ResponseEntity.ok(fileStorageService.fileExists(filePath));
    }
}

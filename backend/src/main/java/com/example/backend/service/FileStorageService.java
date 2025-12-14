package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service for storing and retrieving files.
 * Supports different file categories (submissions, materials, attachments, etc.)
 */
@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
            // Create subdirectories for different file types
            Files.createDirectories(rootLocation.resolve("submissions"));
            Files.createDirectories(rootLocation.resolve("materials"));
            Files.createDirectories(rootLocation.resolve("attachments"));
            Files.createDirectories(rootLocation.resolve("profiles"));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directories", e);
        }
    }

    /**
     * Store a file in the specified category directory.
     * 
     * @param file The file to store
     * @param category The category/subdirectory (e.g., "submissions", "materials")
     * @return The stored file path relative to the upload directory
     */
    public String storeFile(MultipartFile file, String category) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        
        // Security check
        if (originalFilename.contains("..")) {
            throw new RuntimeException("Cannot store file with relative path outside current directory: " + originalFilename);
        }

        // Generate unique filename to prevent overwrites
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + fileExtension;

        try {
            Path categoryPath = rootLocation.resolve(category);
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath);
            }

            Path destinationFile = categoryPath.resolve(uniqueFilename).normalize();
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Return relative path for storage in database
            return category + "/" + uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + originalFilename, e);
        }
    }

    /**
     * Store a submission file (PDF, ZIP, etc.)
     */
    public String storeSubmission(MultipartFile file, Long studentId, Long assignmentId) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        String customFilename = "student" + studentId + "_assignment" + assignmentId + "_" + System.currentTimeMillis() + fileExtension;

        try {
            Path submissionsPath = rootLocation.resolve("submissions");
            Path destinationFile = submissionsPath.resolve(customFilename).normalize();
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return "submissions/" + customFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store submission file", e);
        }
    }

    /**
     * Store a material file (PDF, video, etc.)
     */
    public String storeMaterial(MultipartFile file, Long courseId) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        String customFilename = "course" + courseId + "_" + UUID.randomUUID().toString() + fileExtension;

        try {
            Path materialsPath = rootLocation.resolve("materials");
            Path destinationFile = materialsPath.resolve(customFilename).normalize();
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return "materials/" + customFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store material file", e);
        }
    }

    /**
     * Store a post attachment
     */
    public String storeAttachment(MultipartFile file, Long postId) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        String customFilename = "post" + postId + "_" + UUID.randomUUID().toString() + fileExtension;

        try {
            Path attachmentsPath = rootLocation.resolve("attachments");
            Path destinationFile = attachmentsPath.resolve(customFilename).normalize();
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return "attachments/" + customFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store attachment", e);
        }
    }

    /**
     * Load a file as a Resource for downloading.
     * 
     * @param filePath The relative file path (e.g., "submissions/file.pdf")
     * @return Resource for the file
     */
    public Resource loadFileAsResource(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath).normalize();
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }

    /**
     * Delete a file.
     * 
     * @param filePath The relative file path
     * @return true if deleted successfully
     */
    public boolean deleteFile(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath).normalize();
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filePath, e);
        }
    }

    /**
     * Check if a file exists.
     */
    public boolean fileExists(String filePath) {
        Path file = rootLocation.resolve(filePath).normalize();
        return Files.exists(file);
    }

    /**
     * Get the content type of a file based on extension.
     */
    public String getContentType(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return switch (extension) {
            case ".pdf" -> "application/pdf";
            case ".doc" -> "application/msword";
            case ".docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case ".zip" -> "application/zip";
            case ".rar" -> "application/x-rar-compressed";
            case ".png" -> "image/png";
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".gif" -> "image/gif";
            case ".mp4" -> "video/mp4";
            case ".mp3" -> "audio/mpeg";
            case ".txt" -> "text/plain";
            case ".csv" -> "text/csv";
            case ".json" -> "application/json";
            case ".xml" -> "application/xml";
            case ".ppt" -> "application/vnd.ms-powerpoint";
            case ".pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case ".xls" -> "application/vnd.ms-excel";
            case ".xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default -> "application/octet-stream";
        };
    }

    /**
     * Get file size in bytes.
     */
    public long getFileSize(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath).normalize();
            return Files.size(file);
        } catch (IOException e) {
            return 0;
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}

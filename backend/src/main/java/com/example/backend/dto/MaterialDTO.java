package com.example.backend.dto;

import java.time.LocalDateTime;

public class MaterialDTO {
    private String materialId;
    private String courseCode;
    private String materialTitle;
    private String materialType;
    private String fileSize;
    private String fileName;
    private LocalDateTime uploadDate;
    private String downloadUrl;

    public MaterialDTO() {}

    public MaterialDTO(String materialId, String courseCode, String materialTitle, String materialType,
                       String fileSize, String fileName, LocalDateTime uploadDate, String downloadUrl) {
        this.materialId = materialId;
        this.courseCode = courseCode;
        this.materialTitle = materialTitle;
        this.materialType = materialType;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.downloadUrl = downloadUrl;
    }

    public String getMaterialId() { return materialId; }
    public void setMaterialId(String materialId) { this.materialId = materialId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getMaterialTitle() { return materialTitle; }
    public void setMaterialTitle(String materialTitle) { this.materialTitle = materialTitle; }

    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }

    public String getFileSize() { return fileSize; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
}
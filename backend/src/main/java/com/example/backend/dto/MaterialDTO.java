package com.example.backend.dto;

import java.time.LocalDateTime;

public class MaterialDTO {
    private String materialId;
    private String courseCode;
    private String courseName;
    private String materialTitle;
    private String materialType;
    private String fileSize;
    private String formattedFileSize;
    private String fileName;
    private LocalDateTime uploadDate;
    private String downloadUrl;
    private String iconName;
    private String iconColor;

    public MaterialDTO() {}

    public MaterialDTO(String materialId, String courseCode, String courseName, String materialTitle,
                       String materialType, String fileSize, String formattedFileSize, String fileName,
                       LocalDateTime uploadDate, String downloadUrl, String iconName, String iconColor) {
        this.materialId = materialId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.materialTitle = materialTitle;
        this.materialType = materialType;
        this.fileSize = fileSize;
        this.formattedFileSize = formattedFileSize;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.downloadUrl = downloadUrl;
        this.iconName = iconName;
        this.iconColor = iconColor;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFormattedFileSize() {
        return formattedFileSize;
    }

    public void setFormattedFileSize(String formattedFileSize) {
        this.formattedFileSize = formattedFileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }
}
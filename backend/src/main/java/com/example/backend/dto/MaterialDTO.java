package com.example.backend.dto;

import com.example.backend.Utils.MaterialType;

import java.time.LocalDateTime;

public class MaterialDTO {
    private String materialId;
    private String courseCode;
    private String courseName;
    private String title;
    private MaterialType type;
    private String fileSize;
    private String formattedFileSize;
    private String fileName;
    private LocalDateTime uploadDate;
    private String url;
    private String iconName;
    private String iconColor;
    private String description;

    public MaterialDTO() {}

    public MaterialDTO(String materialId, String courseCode, String courseName, String materialTitle,
                       MaterialType materialType, String fileSize, String formattedFileSize, String fileName,
                       LocalDateTime uploadDate, String downloadUrl, String iconName, String iconColor) {
        this.materialId = materialId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.title = materialTitle;
        this.type = materialType;
        this.fileSize = fileSize;
        this.formattedFileSize = formattedFileSize;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.url = downloadUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
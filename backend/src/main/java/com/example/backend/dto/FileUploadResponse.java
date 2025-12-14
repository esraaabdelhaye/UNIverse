package com.example.backend.dto;

/**
 * Response DTO for file upload operations.
 */
public class FileUploadResponse {
    private String fileName;
    private String filePath;
    private String fileType;
    private long size;
    private String downloadUrl;
    private String message;
    private boolean success;

    public FileUploadResponse() {}

    public FileUploadResponse(String fileName, String filePath, String fileType, long size, String downloadUrl) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.success = true;
        this.message = "File uploaded successfully";
    }

    public static FileUploadResponse success(String fileName, String filePath, String fileType, long size) {
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setFilePath(filePath);
        response.setFileType(fileType);
        response.setSize(size);
        response.setDownloadUrl("/api/files/download/" + filePath);
        response.setSuccess(true);
        response.setMessage("File uploaded successfully");
        return response;
    }

    public static FileUploadResponse error(String message) {
        FileUploadResponse response = new FileUploadResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package com.example.backend.dto.request;

public class UploadMaterialRequest {
    private String courseCode;
    private String materialTitle;
    private String materialType;
    private String description;
    private String[] tags;
    private byte[] fileContent;
    private String fileName;
    private String fileSize;

    public UploadMaterialRequest() {}

    public UploadMaterialRequest(String courseCode, String materialTitle, String materialType,
                                 String description, String[] tags, byte[] fileContent,
                                 String fileName, String fileSize) {
        this.courseCode = courseCode;
        this.materialTitle = materialTitle;
        this.materialType = materialType;
        this.description = description;
        this.tags = tags;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getMaterialTitle() { return materialTitle; }
    public void setMaterialTitle(String materialTitle) { this.materialTitle = materialTitle; }

    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public byte[] getFileContent() { return fileContent; }
    public void setFileContent(byte[] fileContent) { this.fileContent = fileContent; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileSize() { return fileSize; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }
}

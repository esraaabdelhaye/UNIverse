package com.example.backend.dto.request;

public class CreateStudentGroupRequest {
    private String groupName;
    private String groupDescription;
    private byte[] groupImage;

    public CreateStudentGroupRequest() {}

    public CreateStudentGroupRequest(String groupName, String groupDescription, byte[] groupImage) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupImage = groupImage;
    }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getGroupDescription() { return groupDescription; }
    public void setGroupDescription(String groupDescription) { this.groupDescription = groupDescription; }

    public byte[] getGroupImage() { return groupImage; }
    public void setGroupImage(byte[] groupImage) { this.groupImage = groupImage; }
}



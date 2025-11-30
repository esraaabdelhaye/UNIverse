package com.example.backend.dto;

import java.time.LocalDateTime;

public class StudentGroupDTO {
    private String groupId;
    private String groupName;
    private String groupDescription;
    private Integer memberCount;
    private String activityLevel;
    private LocalDateTime createdDate;

    public StudentGroupDTO() {}

    public StudentGroupDTO(String groupId, String groupName, String groupDescription,
                           Integer memberCount, String activityLevel, LocalDateTime createdDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.memberCount = memberCount;
        this.activityLevel = activityLevel;
        this.createdDate = createdDate;
    }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getGroupDescription() { return groupDescription; }
    public void setGroupDescription(String groupDescription) { this.groupDescription = groupDescription; }

    public Integer getMemberCount() { return memberCount; }
    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}

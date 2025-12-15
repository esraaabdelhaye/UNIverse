package com.example.backend.dto;

import java.util.List;

public class PerformanceMetricsDTO {
    private Double avgStudentFeedback;
    private Double courseSuccessRate;
    private Integer publicationsCount;
    private String timetableGenerationTime;
    private Double courseApprovalRate;
    private Double resourceConflictPercentage;
    private Double systemUptimePercentage;

    // Dashboard Stats
    private Integer totalUsers;
    private Integer activeCourses;
    private Integer pendingApprovals;
    private List<String> systemAlerts;

    public PerformanceMetricsDTO() {}

    public PerformanceMetricsDTO(Double avgStudentFeedback, Double courseSuccessRate, Integer publicationsCount,
                                 String timetableGenerationTime, Double courseApprovalRate, Double resourceConflictPercentage,
                                 Double systemUptimePercentage, Integer totalUsers, Integer activeCourses, 
                                 Integer pendingApprovals, List<String> systemAlerts) {
        this.avgStudentFeedback = avgStudentFeedback;
        this.courseSuccessRate = courseSuccessRate;
        this.publicationsCount = publicationsCount;
        this.timetableGenerationTime = timetableGenerationTime;
        this.courseApprovalRate = courseApprovalRate;
        this.resourceConflictPercentage = resourceConflictPercentage;
        this.systemUptimePercentage = systemUptimePercentage;
        this.totalUsers = totalUsers;
        this.activeCourses = activeCourses;
        this.pendingApprovals = pendingApprovals;
        this.systemAlerts = systemAlerts;
    }
    
    // ... Existing Getters/Setters ...
    public Double getAvgStudentFeedback() { return avgStudentFeedback; }
    public void setAvgStudentFeedback(Double avgStudentFeedback) { this.avgStudentFeedback = avgStudentFeedback; }

    public Double getCourseSuccessRate() { return courseSuccessRate; }
    public void setCourseSuccessRate(Double courseSuccessRate) { this.courseSuccessRate = courseSuccessRate; }

    public Integer getPublicationsCount() { return publicationsCount; }
    public void setPublicationsCount(Integer publicationsCount) { this.publicationsCount = publicationsCount; }

    public String getTimetableGenerationTime() { return timetableGenerationTime; }
    public void setTimetableGenerationTime(String timetableGenerationTime) { this.timetableGenerationTime = timetableGenerationTime; }

    public Double getCourseApprovalRate() { return courseApprovalRate; }
    public void setCourseApprovalRate(Double courseApprovalRate) { this.courseApprovalRate = courseApprovalRate; }

    public Double getResourceConflictPercentage() { return resourceConflictPercentage; }
    public void setResourceConflictPercentage(Double resourceConflictPercentage) { this.resourceConflictPercentage = resourceConflictPercentage; }

    public Double getSystemUptimePercentage() { return systemUptimePercentage; }
    public void setSystemUptimePercentage(Double systemUptimePercentage) { this.systemUptimePercentage = systemUptimePercentage; }

    // New Getters/Setters
    public Integer getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Integer totalUsers) { this.totalUsers = totalUsers; }

    public Integer getActiveCourses() { return activeCourses; }
    public void setActiveCourses(Integer activeCourses) { this.activeCourses = activeCourses; }

    public Integer getPendingApprovals() { return pendingApprovals; }
    public void setPendingApprovals(Integer pendingApprovals) { this.pendingApprovals = pendingApprovals; }

    public List<String> getSystemAlerts() { return systemAlerts; }
    public void setSystemAlerts(List<String> systemAlerts) { this.systemAlerts = systemAlerts; }
}
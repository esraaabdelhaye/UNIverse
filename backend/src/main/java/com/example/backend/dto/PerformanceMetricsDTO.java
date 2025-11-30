package com.example.backend.dto;

public class PerformanceMetricsDTO {
    private Double avgStudentFeedback;
    private Double courseSuccessRate;
    private Integer publicationsCount;
    private String timetableGenerationTime;
    private Double courseApprovalRate;
    private Double resourceConflictPercentage;
    private Double systemUptimePercentage;

    public PerformanceMetricsDTO() {}

    public PerformanceMetricsDTO(Double avgStudentFeedback, Double courseSuccessRate, Integer publicationsCount,
                                 String timetableGenerationTime, Double courseApprovalRate, Double resourceConflictPercentage,
                                 Double systemUptimePercentage) {
        this.avgStudentFeedback = avgStudentFeedback;
        this.courseSuccessRate = courseSuccessRate;
        this.publicationsCount = publicationsCount;
        this.timetableGenerationTime = timetableGenerationTime;
        this.courseApprovalRate = courseApprovalRate;
        this.resourceConflictPercentage = resourceConflictPercentage;
        this.systemUptimePercentage = systemUptimePercentage;
    }

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
}
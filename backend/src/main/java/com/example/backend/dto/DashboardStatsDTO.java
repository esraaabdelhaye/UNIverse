package com.example.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardStatsDTO {
    private int totalUsers;
    private int totalFaculty;
    private int totalStudents;
    private int activeCourses;
    private int pendingApprovals;
    private int totalCourses;
    private List<DashboardAlert> alerts = new ArrayList<>();

    public static class DashboardAlert {
        private String type; // "warning", "info", "success"
        private String title;
        private String text;
        private String actionText;

        public DashboardAlert() {}

        public DashboardAlert(String type, String title, String text, String actionText) {
            this.type = type;
            this.title = title;
            this.text = text;
            this.actionText = actionText;
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getActionText() { return actionText; }
        public void setActionText(String actionText) { this.actionText = actionText; }
    }

    // Getters and Setters
    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }

    public int getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(int totalFaculty) { this.totalFaculty = totalFaculty; }

    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }

    public int getActiveCourses() { return activeCourses; }
    public void setActiveCourses(int activeCourses) { this.activeCourses = activeCourses; }

    public int getPendingApprovals() { return pendingApprovals; }
    public void setPendingApprovals(int pendingApprovals) { this.pendingApprovals = pendingApprovals; }

    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }

    public List<DashboardAlert> getAlerts() { return alerts; }
    public void setAlerts(List<DashboardAlert> alerts) { this.alerts = alerts; }
}

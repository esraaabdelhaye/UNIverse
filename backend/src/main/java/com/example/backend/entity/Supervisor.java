package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("SUPERVISOR") // Single table inheritance marker
public class Supervisor extends Doctor {

    @Column(name = "supervisor_role", length = 100)
    private String supervisorRole;

    // Simple representation for managedProgram For now
    @Column(name = "managed_program", length = 100)
    private String managedProgram;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 50)
    private String status;

    // Simple many-to-many with Course
    @ManyToMany
    @JoinTable(
            name = "supervisor_course",
            joinColumns = @JoinColumn(name = "supervisor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> coordinatedCourses = new HashSet<>();

    @OneToOne(mappedBy = "coordinator")
    private Department CoordinatedDepartment;

    // Relation with announcement
    @OneToMany(mappedBy = "supervisorAuthor")
    private List<Announcement> announcements = new ArrayList<>();

    // Relation with Events
    @OneToMany(mappedBy = "supervisor")
    private List<Event> events = new ArrayList<>();

    public Supervisor() {
    }

    // You can remove optional fields
    public Supervisor(Long id, String name, String email, Department department, String phoneNumber, String officeLocation,
                      String title, String expertise, String supervisorRole, Department coordinatedDepartment,
                      String managedProgram, LocalDate startDate, LocalDate endDate, String status)
    {
        super(id, name, email, department, phoneNumber, officeLocation, title, expertise);
        this.supervisorRole = supervisorRole;
        this.managedProgram = managedProgram;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and Setters
    public String getSupervisorRole() {
        return supervisorRole;
    }

    public String getManagedProgram() {
        return managedProgram;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setSupervisorRole(String supervisorRole) {
        this.supervisorRole = supervisorRole;
    }

    public void setManagedProgram(String managedProgram) {
        this.managedProgram = managedProgram;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Course> getCoordinatedCourses() {
        return coordinatedCourses;
    }

    public void setCoordinatedCourses(Set<Course> coordinatedCourses) {
        this.coordinatedCourses = coordinatedCourses;
    }

    public Department getCoordinatedDepartment() {
        return CoordinatedDepartment;
    }

    public void setCoordinatedDepartment(Department coordinatedDepartment) {
        CoordinatedDepartment = coordinatedDepartment;
    }

    public void addCoordinatedCourse(Course course) {
        coordinatedCourses.add(course);
        course.addCoordinator(this);
    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setSupervisorAuthor(this);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setSupervisor(this);
    }
}

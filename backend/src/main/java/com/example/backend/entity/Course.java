package com.example.backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", unique = true, nullable = false, length = 20)
    private String courseCode;

    @Column(name = "course_name", nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer credits;

    @Column(length = 20)
    private String semester;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 20)
    private String status = "Open";

    @Column(name = "capacity")
    private Integer capacity = 50; // Default capacity

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<CourseEnrollment> enrollments = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Doctor> doctors = new HashSet<>();

    // Many-to-Many with TAs
    @ManyToMany(mappedBy = "assistedCourses")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<TeachingAssistant> tas = new HashSet<>();

    // Courses that are prerequisites for this course
    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    // Courses for which this course is a prerequisite
    @ManyToMany(mappedBy = "prerequisites")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Course> dependentCourses = new HashSet<>();

    //Relation with coordinator
    @ManyToMany(mappedBy = "coordinatedCourses")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Supervisor> coordinators = new HashSet<>();

    //Relation with materials
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Material> materials = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<AssignmentSubmission> submissions = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Announcement> announcements = new HashSet<>();

    public Course(Long id, String courseCode, String name, Integer credits,
                  String semester, String description, Department department)
    {
        this.id = id;
        this.courseCode = courseCode;
        this.name = name;
        this.credits = credits;
        this.semester = semester;
        this.description = description;
        this.department = department;
    }

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getSemester() {
        return semester;
    }

    public String getDescription() {
        return description;
    }

    public Department getDepartment() {
        return department;
    }

    public Set<CourseEnrollment> getEnrollments() {
        return enrollments;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Set<TeachingAssistant> getTas() {
        return tas;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public Set<Course> getDependentCourses() {
        return dependentCourses;
    }

    public Set<Supervisor> getCoordinators() {
        return coordinators;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public Set<AssignmentSubmission> getSubmissions() {
        return submissions;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEnrollments(Set<CourseEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setTas(Set<TeachingAssistant> tas) {
        this.tas = tas;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setCoordinators(Set<Supervisor> coordinators) {
        this.coordinators = coordinators;
    }

    public void setDependentCourses(Set<Course> dependentCourses) {
        this.dependentCourses = dependentCourses;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void setSubmissions(Set<AssignmentSubmission> submissions) {
        this.submissions = submissions;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.getCourses().add(this);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
        doctor.getCourses().remove(this);
    }

    public void addTeachingAssistant(TeachingAssistant ta) {
        tas.add(ta);
        ta.getAssistedCourses().add(this);
    }

    public void removeTeachingAssistant(TeachingAssistant ta) {
        tas.remove(ta);
        ta.getAssistedCourses().remove(this);
    }

    public void addPrerequisite(Course prerequisite) {
        prerequisites.add(prerequisite);
        prerequisite.getDependentCourses().add(this);
    }

    public void removePrerequisite(Course prerequisite) {
        prerequisites.remove(prerequisite);
        prerequisite.getDependentCourses().remove(this);
    }

    public void addDependentCourse(Course dependent) {
        dependentCourses.add(dependent);
        dependent.getPrerequisites().add(this);
    }

    public void removeDependentCourse(Course dependent) {
        dependentCourses.remove(dependent);
        dependent.getPrerequisites().remove(this);
    }

    public void addCoordinator(Supervisor coordinator) {
        this.coordinators.add(coordinator);
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
        material.setCourse(this);
    }

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setCourse(this);
    }

    public void removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setCourse(null);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
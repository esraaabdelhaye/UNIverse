package com.example.backend.entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "course")
    private Set<CourseEnrollment> enrollments = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<Doctor> doctors = new HashSet<>();

    // Many-to-Many with TAs
    @ManyToMany(mappedBy = "assistedCourses")
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
    private Set<Course> dependentCourses = new HashSet<>();

    //Relation with coordinator
    @ManyToMany(mappedBy = "coordinatedCourses")
    private Set<Supervisor> coordinators = new HashSet<>();


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

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<CourseEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<CourseEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<TeachingAssistant> getTas() {
        return tas;
    }

    public void setTas(Set<TeachingAssistant> tas) {
        this.tas = tas;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Set<Course> getDependentCourses() {
        return dependentCourses;
    }

    public Set<Supervisor> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(Set<Supervisor> coordinators) {
        this.coordinators = coordinators;
    }

    public void setDependentCourses(Set<Course> dependentCourses) {
        this.dependentCourses = dependentCourses;
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
        this.coordinators.add(coordinator) ;
    }


}

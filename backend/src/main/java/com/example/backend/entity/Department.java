package com.example.backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false, length = 100)
    private String name;

    // Relation with Supervisor
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"coordinatedDepartment", "department"})
    private Supervisor coordinator;

    // Relation between students and department
    @OneToMany(mappedBy = "department")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Student> students = new HashSet<>();

    // Relation with doctors
    @OneToMany(mappedBy = "department")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Doctor> doctors = new HashSet<>();

    public Department(Long id, String name, Supervisor coordinator)
    {
        this.id = id;
        this.name = name;
        this.coordinator = coordinator;
    }

    public Department() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supervisor getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Supervisor coordinator) {
        this.coordinator = coordinator;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setDepartment(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setDepartment(null);
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.setDepartment(this);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
        doctor.setDepartment(null);
    }
}

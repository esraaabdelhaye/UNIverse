package com.example.backend.entity;

import com.example.backend.Utils.NotificationType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false, length = 255)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisorCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Doctor doctorCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taCreator;

    // Currently only student list
    // can add any other needed lists
    // for simplicity the relation is one-sided
    @ManyToMany
    @JoinTable(
            name = "notification_students",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentRecipients = new ArrayList<>();

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Supervisor getSupervisorCreator() {
        return supervisorCreator;
    }

    public void setSupervisorCreator(Supervisor supervisorCreator) {
        this.supervisorCreator = supervisorCreator;
    }

    public Doctor getDoctorCreator() {
        return doctorCreator;
    }

    public void setDoctorCreator(Doctor doctorCreator) {
        this.doctorCreator = doctorCreator;
    }

    public TeachingAssistant getTaCreator() {
        return taCreator;
    }

    public void setTaCreator(TeachingAssistant taCreator) {
        this.taCreator = taCreator;
    }

    public List<Student> getStudentRecipients() {
        return studentRecipients;
    }

    public void setStudentRecipients(List<Student> studentRecipients) {
        this.studentRecipients = studentRecipients;
    }

    public void addStudent(Student student) {
        studentRecipients.add(student);
    }
}

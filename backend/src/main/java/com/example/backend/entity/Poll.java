package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Creator relations
    // These relations are currently one-sided
    // this field can be removed
    // in case there is no special
    // type of polls that required
    // the creator to be supervisor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisorCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Doctor doctorCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_id")
    private TeachingAssistant taCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentrep_id")
    private StudentRepresentative studentRepresentative;

    // One-to-many relation to options (owning side is PollOption)
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options = new ArrayList<>();

    // Optional: track student voters to prevent double voting
    @ManyToMany
    @JoinTable(
            name = "poll_votes",
            joinColumns = @JoinColumn(name = "poll_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> voters = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public List<Student> getVoters() {
        return voters;
    }

    public void setVoters(List<Student> voters) {
        this.voters = voters;
    }

    public StudentRepresentative getStudentRepresentative() {
        return studentRepresentative;
    }

    public void setStudentRepresentative(StudentRepresentative studentRepresentative) {
        this.studentRepresentative = studentRepresentative;
    }

    public void addOption(PollOption option) {
        option.setPoll(this);
        this.options.add(option);
    }

    public void addVoter(Student student) {
        if (!voters.contains(student)) {
            voters.add(student);
        }
    }
}

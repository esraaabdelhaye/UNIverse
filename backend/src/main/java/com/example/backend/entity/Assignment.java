package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description; // TEXT

    @Column(nullable = false, length = 150)
    private LocalDateTime dueDate;

    @Column(precision = 5, scale = 2)
    private Integer maxScore;

    //Relation with course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Relation with students
    @OneToMany(mappedBy = "assignment")
    private Set<AssignmentSubmission> submissions = new HashSet<>();

    @Column(name = "assignment_path")
    private String assignmentPath;

    public Assignment(Integer id, String title, String description, LocalDateTime dueDate,
                      Course course, Integer maxScore)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.course = course;
        this.maxScore = maxScore;
    }

    public Assignment() {
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<AssignmentSubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<AssignmentSubmission> submissions) {
        this.submissions = submissions;
    }

    public void addSubmission(AssignmentSubmission submission) {
        submissions.add(submission);
        submission.setAssignment(this);
    }
    public void removeSubmission(AssignmentSubmission submission) {
        submissions.remove(submission);
        submission.setAssignment(null);
    }
    public String getAssignmentPath() {
        return assignmentPath;
    }
    public void setAssignmentPath(String assignmentPath) {
        this.assignmentPath = assignmentPath;
    }

}

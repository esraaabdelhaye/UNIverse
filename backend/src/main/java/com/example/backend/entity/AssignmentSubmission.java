package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    //Relation with student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Relation with courses
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Relation with Assignment
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    // Submission details
    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "status", length = 50)
    private String status; // e.g., submitted, pending, late, graded

    @Column(name = "feedback", length = 500)
    private String feedback;

    @Column(name = "submission_file", length = 500)
    private String submissionFile; // file path or URL

    public AssignmentSubmission() {}

    public AssignmentSubmission(long id, Student student, Course course, Assignment assignment,
                                LocalDate submissionDate, String grade, String status,
                                String feedback, String submissionFile)
    {
        this.id = id;
        this.student = student;
        this.course = course;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.grade = grade;
        this.status = status;
        this.feedback = feedback;
        this.submissionFile = submissionFile;
    }

    public long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public String getGrade() {
        return grade;
    }

    public String getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getSubmissionFile() {
        return submissionFile;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setSubmissionFile(String submissionFile) {
        this.submissionFile = submissionFile;
    }

    //Add any helper methods needed
}

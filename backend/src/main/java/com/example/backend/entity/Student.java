package com.example.backend.entity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "student_type", discriminatorType = DiscriminatorType.STRING)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(name = "name" , nullable = false , length = 100)
    private String name ;

    @Column(name = "email" , nullable = false , length = 100 ,  unique = true)
    private String email;

    @Column(name = "academic_id", nullable = false , unique = true)
    private long academicId ;

    // Relation with courses via linking table
    @OneToMany(mappedBy = "student")
    private Set<CourseEnrollment> enrollments = new HashSet<>();

    // Relation with department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Relation with Assignments
    @OneToMany(mappedBy = "student")
    private Set<AssignmentSubmission> submissions = new HashSet<>();

    public Student() {

    }
    public Student(long id, String name, String email, long academicId,Department department)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.academicId = academicId;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getAcademicId() {
        return academicId;
    }

    public Set<CourseEnrollment> getEnrollments() {
        return enrollments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAcademicId(long academicId) {
        this.academicId = academicId;
    }

    public void setEnrollments(Set<CourseEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setSubmissions(Set<AssignmentSubmission> submissions) {
        this.submissions = submissions;
    }

    public Set<AssignmentSubmission> getSubmissions() {
        return submissions;
    }

    public void addEnrollment(CourseEnrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(CourseEnrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }

    public void addSubmission(AssignmentSubmission submission) {
        submissions.add(submission);
        submission.setStudent(this);
    }

    public void removeSubmission(AssignmentSubmission submission) {
        submissions.remove(submission);
        submission.setStudent(null);
    }

    // Add any helper methods needed
}

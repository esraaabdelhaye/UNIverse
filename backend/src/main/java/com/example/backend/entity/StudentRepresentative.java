package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("REPRESENTATIVE")
public class StudentRepresentative extends Student {

    @Column(length = 50)
    private String role;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 1000)
    private String responsibilities;

    @Column(length = 50)
    private String status;

    @ManyToMany
    @JoinTable(
            name = "studentrep_posts",
            joinColumns = @JoinColumn(name = "studetrep_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts = new HashSet<>();


    public StudentRepresentative() {
    }

    public StudentRepresentative(long id, String name, String email, long academicId, Department department,
                                 String role, LocalDate startDate, LocalDate endDate, String responsibilities,
                                 String status, Set<Post> posts)
    {
        super(id, name, email, academicId, department);
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.responsibilities = responsibilities;
        this.status = status;
        this.posts = posts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    public void addPost(Post post) {
        this.posts.add(post);
        post.addStudentRep(this);
    }


}

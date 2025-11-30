package com.example.backend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class TeachingAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;


    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "office_location", length = 100)
    private String officeLocation;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String expertise;

    //Relation with department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Many-to-Many with Course
    @ManyToMany
    @JoinTable(
            name = "teaching_assistant_course",
            joinColumns = @JoinColumn(name = "teaching_assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> assistedCourses = new HashSet<>();

    // Many-to-Many with Post
    @ManyToMany
    @JoinTable(
            name = "teaching_assistant_post",
            joinColumns = @JoinColumn(name = "teaching_assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts = new HashSet<>();

    public TeachingAssistant(long id, String name, String email, String phoneNumber,
                             String officeLocation, String title, String expertise,
                             Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.title = title;
        this.expertise = expertise;
        this.department = department;
        this.assistedCourses = assistedCourses;
        this.posts = posts;
    }

    public TeachingAssistant() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Course> getAssistedCourses() {
        return assistedCourses;
    }

    public void setAssistedCourses(Set<Course> assistedCourses) {
        this.assistedCourses = assistedCourses;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.addTeachingAssistant(this);
    }

    public void addCourse(Course course) {
        this.assistedCourses.add(course);

    }


}

package com.example.backend.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "hashed_password", nullable = false , length = 100)
    private String hashedPassword ;

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

    @ManyToMany
    @JoinTable(
            name = "teaching_assistant_post",
            joinColumns = @JoinColumn(name = "teaching_assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Post> posts = new HashSet<>();

    // Relation with announcements
    @OneToMany(mappedBy = "taAuthor")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Announcement> announcements = new ArrayList<>();

    // Relation with Events
    @OneToMany(mappedBy = "ta")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "taUploader")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Material> materials = new ArrayList<>();

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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.addTeachingAssistant(this);
    }

    public void addCourse(Course course) {
        this.assistedCourses.add(course);

    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setTaAuthor(this);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setTa(this);
    }

    public void addMaterial(Material material) {
        material.setTaUploader(this);
        this.materials.add(material);
    }

}

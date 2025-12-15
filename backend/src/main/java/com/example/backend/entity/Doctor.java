package com.example.backend.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "doctor_type", discriminatorType = DiscriminatorType.STRING)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // doctor id

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

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

    @Column(name = "status", length = 20)
    private String status = "Active";


    // Linking tables
    @ManyToMany
    @JoinTable(
            name = "doctor_course",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Course> courses = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "doctor_posts",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "doctorAuthor")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Announcement> announcements = new ArrayList<>();


    @OneToMany(mappedBy = "doctor")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "doctorUploader")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Material> materials = new ArrayList<>();

    public Doctor() {}

    public Doctor(Long id, String name, String email, Department department,
                  String phoneNumber, String officeLocation, String title, String expertise)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.officeLocation = officeLocation;
        this.title = title;
        this.expertise = expertise;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
        post.addDoctor(this);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.addDoctor(this);
    }

    public void addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setDoctorAuthor(this);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setDoctor(this);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

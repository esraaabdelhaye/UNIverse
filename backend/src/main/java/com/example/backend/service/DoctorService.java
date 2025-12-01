package com.example.backend.service;

import com.example.backend.dto.request.CreateAnnouncementRequest;
import com.example.backend.dto.request.CreatePollRequest;
import com.example.backend.dto.request.CreatePostRequest;
import com.example.backend.entity.*;
import com.example.backend.repository.*;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final AnnouncementRepo announcementRepo;
    private final CourseRepo courseRepo;
    private final PostRepo postRepo;
    private final PollRepo pollRepo;

    public DoctorService(DoctorRepo doctorRepo, AnnouncementRepo announcementRepo, CourseRepo courseRepo, PostRepo postRepo, PollRepo pollRepo) {
        this.doctorRepo = doctorRepo;
        this.announcementRepo = announcementRepo;
        this.courseRepo = courseRepo;
        this.postRepo = postRepo;
        this.pollRepo = pollRepo;
    }

    public Announcement createAnnouncement(Doctor doctor, CreateAnnouncementRequest request) {
        Course course = courseRepo.findByCourseCode(request.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + request.getCourseCode()));

        Announcement announcement = new Announcement();
        announcement.setCourse(course);
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setCreatedAt(request.getPublishDate());
        announcement.setVisibility(request.getVisibility());
        announcement.setAttachments(List.of(request.getAttachments()));
        announcement.setDoctorAuthor(doctor); // Author is now doctor
        return announcementRepo.save(announcement);

    }

    public Poll createPoll(Doctor doctor, CreatePollRequest request) {
        Poll poll = new Poll();
        poll.setTitle(request.getPollQuestion());
        poll.setStartTime(LocalDateTime.now());
        poll.setEndTime(request.getEndDate());
        poll.setDoctorCreator(doctor);

        if (request.getOptions() != null) {
            for (String optionText : request.getOptions()) {
                PollOption option = new PollOption();
                option.setText(optionText);
                option.setVoteCount(0);
                poll.addOption(option);
            }
        }

        return pollRepo.save(poll);
    }

    public Post createPost(Doctor doctor, CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getPostContent().length() > 50
                ? request.getPostContent().substring(0, 50)
                : request.getPostContent());
        post.setContent(request.getPostContent());
        post.setStatus(request.getPostType());
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.addDoctor(doctor);
        return postRepo.save(post);
    }
}

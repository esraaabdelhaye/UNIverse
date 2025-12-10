package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.PostDTO;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Post;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepo postRepo ;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;
    @Autowired
    public PostService(PostRepo postRepo, DoctorRepo doctorRepo, SupervisorRepo supervisorRepo) {
        this.postRepo = postRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
    }

    public ApiResponse<PostDTO> createPost(PostAuthor postAuthorDTO , PostDTO postDTO) {
        if (postAuthorDTO instanceof DoctorDTO doctorDTO) {
            return createPost(doctorDTO,postDTO);
        }
        if (postAuthorDTO instanceof SupervisorDTO supervisorDTO) {
            return createPost(supervisorDTO,postDTO);
        }
        else return ApiResponse.unauthorized("This user is not authorized");
    }

    /**
     * Post Creation methods
     */

    public ApiResponse<PostDTO> createPost(DoctorDTO doctorDTO, PostDTO postDTO) {
        Optional<Doctor> doctorOpt = doctorRepo.findById(Long.parseLong(doctorDTO.getDoctorId()));
        if(doctorOpt.isEmpty()) return ApiResponse.badRequest("Doctor not found");
        Doctor doctor = doctorOpt.get();
        Post post = new Post();
        post.setContent(postDTO.getPostContent() != null ? postDTO.getPostContent() : "No content");
        // Unify DTOs and DataBase scheme
        post.setTitle(postDTO.getPostContent() != null ? postDTO.getPostContent() : "No content");
        post.addDoctor(doctor);
        post.setCreatedAt(postDTO.getCreatedDate());
        post.setStatus(postDTO.getPostType());
        postRepo.save(post);
        // Currently returning the same DTO
        // will add method to return full data later
        return ApiResponse.success("Created post successfully" ,postDTO) ;
    }

    public ApiResponse<PostDTO> createPost(SupervisorDTO supDto, PostDTO postDTO) {
        Optional<Supervisor> supOpt = supervisorRepo.findById(Long.parseLong(supDto.getEmployeeId()));
        if(supOpt.isEmpty()) return ApiResponse.badRequest("Supervisor not found");
        Supervisor sup = supOpt.get();
        Post post = new Post();
        post.setContent(postDTO.getPostContent() != null ? postDTO.getPostContent() : "No content");
        // Unify DTOs and DataBase scheme
        // No title yet exist in the postDTO
        post.setTitle(postDTO.getPostContent() != null ? postDTO.getPostContent() : "No content");
        post.addDoctor(sup);
        post.setCreatedAt(postDTO.getCreatedDate());
        post.setStatus(postDTO.getPostType());
        postRepo.save(post);
        // Currently returning the same DTO
        // will add method to return full data later
        return ApiResponse.success("Created post successfully" ,postDTO) ;
    }
}

package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.PostDTO;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.request.CreatePostRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Post;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.PostRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostService {

    private final PostRepo postRepo;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;

    @Autowired
    public PostService(PostRepo postRepo, DoctorRepo doctorRepo, SupervisorRepo supervisorRepo) {
        this.postRepo = postRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
    }

    /**
     * Main entry point for post creation
     */
    public ApiResponse<PostDTO> createPost(PostAuthor postAuthorDTO, CreatePostRequest createPostRequest) {
        if (postAuthorDTO instanceof DoctorDTO doctorDTO) {
            return createPostForDoctor(doctorDTO, createPostRequest);
        }
        if (postAuthorDTO instanceof SupervisorDTO supervisorDTO) {
            return createPostForSupervisor(supervisorDTO, createPostRequest);
        }
        return ApiResponse.unauthorized("This user is not authorized");
    }

    /**
     * Post Creation methods
     */
    private ApiResponse<PostDTO> createPostForDoctor(DoctorDTO doctorDTO, CreatePostRequest createPostRequest) {
        try {
            Long doctorId = Long.parseLong(doctorDTO.getDoctorId());
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.badRequest("Doctor not found");
            }

            Doctor doctor = doctorOpt.get();
            Post post = buildPostEntity(createPostRequest);
            post.addDoctor(doctor);
            postRepo.save(post);

            return ApiResponse.success("Created post successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid doctor ID format");
        }
    }

    private ApiResponse<PostDTO> createPostForSupervisor(SupervisorDTO supervisorDTO, CreatePostRequest createPostRequest) {
        try {
            Long supervisorId = Long.parseLong(supervisorDTO.getEmployeeId());
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.badRequest("Supervisor not found");
            }

            Supervisor supervisor = supervisorOpt.get();
            Post post = buildPostEntity(createPostRequest);
            post.addDoctor(supervisor);
            postRepo.save(post);

            return ApiResponse.success("Created post successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid supervisor ID format");
        }
    }

    /**
     * Post Modification methods
     */
    public ApiResponse<PostDTO> updatePost(PostAuthor author, PostDTO postDTO) {
        try {
            Long postId = Long.parseLong(postDTO.getPostId());
            Optional<Post> postOpt = postRepo.findById(postId);

            if (postOpt.isEmpty()) {
                return ApiResponse.badRequest("Post not found");
            }

            Post post = postOpt.get();

            // Verify authorization
            ApiResponse<Void> authCheck = verifyPostOwnership(author, post);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            // Update post fields
            if (postDTO.getTitle() != null) {
                post.setTitle(postDTO.getTitle());
            }
            if (postDTO.getPostContent() != null) {
                post.setContent(postDTO.getPostContent());
            }
            if (postDTO.getTags() != null) {
                post.setTags(Arrays.toString(postDTO.getTags()));
            }

            post.setUpdatedAt(LocalDateTime.now());
            post.setLikeCount(postDTO.getLikeCount());
            post.setCommentCount(postDTO.getCommentCount());

            postRepo.save(post);

            return ApiResponse.success("Updated post successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    /**
     * Currently these methods add and remove likes blindly but I will
     * Edit them to store the user id and likes in hash map
     */
    public ApiResponse<PostDTO> addLike(PostDTO postDTO) {
        try {
            Long postId = Long.parseLong(postDTO.getPostId());
            Optional<Post> postOpt = postRepo.findById(postId);

            if (postOpt.isEmpty()) {
                return ApiResponse.badRequest("Post not found");
            }

            Post post = postOpt.get();
            post.setLikeCount(post.getLikeCount() + 1);
            postRepo.save(post);

            return ApiResponse.success("Added like successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    public ApiResponse<PostDTO> removeLike(PostDTO postDTO) {
        try {
            Long postId = Long.parseLong(postDTO.getPostId());
            Optional<Post> postOpt = postRepo.findById(postId);

            if (postOpt.isEmpty()) {
                return ApiResponse.badRequest("Post not found");
            }

            Post post = postOpt.get();

            // Prevent negative like count
            if (post.getLikeCount() > 0) {
                post.setLikeCount(post.getLikeCount() - 1);
            }

            postRepo.save(post);

            return ApiResponse.success("Removed like successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    /**
     * The same here for comments i will try to do the same trick
     * Hash map the user id as key list of comments as value
     */
    public ApiResponse<PostDTO> addComment(String comment, PostDTO postDTO) {
        try {
            Optional<Post> postOpt = postRepo.findById(Long.valueOf(postDTO.getPostId())) ;
            if (postOpt.isEmpty()) {
                return ApiResponse.badRequest("Post not found");
            }

            Post post = postOpt.get();
            post.addComment(comment);
            return ApiResponse.success("Created post successfully", convertToDTO(post));
        }
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    public ApiResponse<PostDTO> removeComment(String comment, PostDTO postDTO) {
        try {
            Long postId = Long.parseLong(postDTO.getPostId());
            Optional<Post> postOpt = postRepo.findById(postId);

            if (postOpt.isEmpty()) {
                return ApiResponse.badRequest("Post not found");
            }

            Post post = postOpt.get();
            boolean removed = post.removeComment(comment);

            if (!removed) {
                return ApiResponse.badRequest("Comment not found");
            }

            postRepo.save(post);
            return ApiResponse.success("Removed comment successfully", convertToDTO(post));
        }
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    /**
     * Post deletion methods
     */

    public ApiResponse<PostDTO> deletePost(PostAuthor author, PostDTO postDTO) {
        try {
            Optional<Post> postOpt = postRepo.findById(Long.parseLong(postDTO.getPostId()));
            if (postOpt.isEmpty()) return ApiResponse.badRequest("Post not found");
            Post post = postOpt.get();
            ApiResponse<Void> authCheck = verifyPostOwnership(author, post);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }
            postRepo.delete(post);
            return ApiResponse.success("Deleted post successfully", convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    /**
     * Post retrieval methods
     */
    public ApiResponse<PostDTO> getPost(String postId) {
        try {
            Optional<Post> postOpt = postRepo.findById(Long.parseLong(postId));
            if (postOpt.isEmpty()) return ApiResponse.badRequest("Post not found");
            Post post = postOpt.get();
            return ApiResponse.success("fecthed post successfuly",convertToDTO(post));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    public ApiResponse<List<PostDTO>> getAllPosts(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
            Page<Post> postsPage = postRepo.findAll(pageable);

            List<PostDTO> postDTOs = postsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Posts retrieved successfully", postDTOs);
        }
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    public ApiResponse<List<PostDTO>> getPostsByAuthor(PostAuthor postAuthor, int page, int pageSize) {
        try {
            long authorID ;
            if (postAuthor != null) {
                if (postAuthor instanceof SupervisorDTO sup) authorID = Long.parseLong(sup.getEmployeeId());
                else if (postAuthor instanceof DoctorDTO doc) authorID = Long.parseLong(doc.getDoctorId());
                else return ApiResponse.unauthorized("Invalid post author");
            }
            else return ApiResponse.unauthorized("Invalid post author");
            List<Post> posts = postRepo.findByDoctors_Id(authorID) ;

            List<PostDTO> postDTOs = posts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Author's posts retrieved successfully", postDTOs);
        }
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid post ID format");
        }
    }

    public ApiResponse<List<PostDTO>> getPostsByStatus(String status) {
        List<Post> posts = postRepo.findByStatus(status);

        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Posts retrieved successfully", postDTOs);
    }

    public ApiResponse<List<PostDTO>> searchPosts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.badRequest("Search keyword cannot be empty");
        }

        List<Post> posts = postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Search completed", postDTOs);
    }


    /**
     * Helper methods
     */
    private Post buildPostEntity(CreatePostRequest request) {
        Post post = new Post();

        String content = request.getPostContent() != null && !request.getPostContent().trim().isEmpty()
                ? request.getPostContent()
                : "No content";

        post.setContent(content);
        post.setTitle(content); // TODO: Separate title field in CreatePostRequest
        post.setCreatedAt(LocalDateTime.now());
        post.setStatus(request.getPostType());
        post.setAttachmentFile(request.getAttachmentFile());

        // Handle tags safely
        if (request.getTags() != null && request.getTags().length > 0) {
            post.setTags(Arrays.toString(request.getTags()));
        } else {
            post.setTags("[]");
        }

        return post;
    }

    private ApiResponse<Void> verifyPostOwnership(PostAuthor author, Post post) {
        if (post.getDoctors() == null || post.getDoctors().isEmpty()) {
            return ApiResponse.serviceUnavailable("Post has no associated author");
        }

        Long authorId = post.getDoctors().iterator().next().getId();

        try {
            if (author instanceof DoctorDTO doctorDTO) {
                Long requesterId = Long.parseLong(doctorDTO.getDoctorId());
                if (!requesterId.equals(authorId)) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } else if (author instanceof SupervisorDTO supervisorDTO) {
                Long requesterId = Long.parseLong(supervisorDTO.getEmployeeId());
                if (!requesterId.equals(authorId)) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } else {
                return ApiResponse.unauthorized("Invalid author type");
            }
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }

        return ApiResponse.success(null);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(String.valueOf(post.getId()));
        postDTO.setPostContent(post.getContent());
        postDTO.setPostType(post.getStatus());
        postDTO.setCreatedDate(post.getCreatedAt());
        postDTO.setTitle(post.getTitle());
        postDTO.setLikeCount(post.getLikeCount());
        postDTO.setCommentCount(post.getCommentCount());

        if (post.getDoctors() != null && !post.getDoctors().isEmpty()) {
            Doctor doc = post.getDoctors().iterator().next();
            postDTO.setAuthorId(String.valueOf(doc.getId()));

            // Determine role based on actual class type
            if (doc instanceof Supervisor) {
                postDTO.setAuthorRole("SUPERVISOR");
            } else {
                postDTO.setAuthorRole("DOCTOR");
            }
        }

        return postDTO;
    }
}
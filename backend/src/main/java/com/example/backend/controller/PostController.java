package com.example.backend.controller;

import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.PostDTO;
import com.example.backend.dto.request.CreatePostRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<ApiResponse<?>> createPost(
            @AuthenticationPrincipal Object postAuthorDTO,
            @RequestBody CreatePostRequest createPostRequest) {
        ApiResponse<?> response = postService.createPost((PostAuthor) postAuthorDTO, createPostRequest);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Post modification end points

    @PostMapping("/modifyPost")
    public ResponseEntity<ApiResponse<PostDTO>> modifyPost(
            @AuthenticationPrincipal Object postAuthorDTO,
            @RequestBody PostDTO postDTO) {
        ApiResponse<PostDTO> response = postService.updatePost((PostAuthor) postAuthorDTO, postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{postId}/likes/add")
    public ResponseEntity<ApiResponse<PostDTO>> addLike(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        ApiResponse<PostDTO> response = postService.addLike(postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{postId}/likes/remove")
    public ResponseEntity<ApiResponse<PostDTO>> removeLike(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        ApiResponse<PostDTO> response = postService.removeLike(postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Using query params for requests is not ideal but for
     * now we will use it until we have enough time
     */
    @PostMapping("/{postId}/comments/add")
    public ResponseEntity<ApiResponse<PostDTO>> addComment(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId,
            @RequestParam String comment) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        ApiResponse<PostDTO> response = postService.addComment(comment, postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{postId}/comments/remove")
    public ResponseEntity<ApiResponse<PostDTO>> removeComment(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId,
            @RequestParam String comment) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        ApiResponse<PostDTO> response = postService.removeComment(comment, postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse<PostDTO>> deletePost(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        ApiResponse<PostDTO> response = postService.deletePost((PostAuthor) postAuthorDTO, postDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Post retrieval endPoints
     */

    @GetMapping("/{postId}/get")
    public ResponseEntity<ApiResponse<PostDTO>> getPost(
            @AuthenticationPrincipal Object postAuthorDTO,
            @PathVariable String postId) {
        ApiResponse<PostDTO> response = postService.getPost(postId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPosts(
            @AuthenticationPrincipal Object postAuthorDTO,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        ApiResponse<List<PostDTO>> response = postService.getAllPosts(page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAuthor")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByAuthor(
            @AuthenticationPrincipal Object postAuthorDTO,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        ApiResponse<List<PostDTO>> response = postService.getPostsByAuthor((PostAuthor) postAuthorDTO, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPostsStatus")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByStatus(
            @AuthenticationPrincipal Object postAuthorDTO,
            @RequestParam String status) {
        ApiResponse<List<PostDTO>> response = postService.getPostsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostDTO>>> searchPosts(
            @RequestParam String keyword,
            @AuthenticationPrincipal Object postAuthorDTO) {
        ApiResponse<List<PostDTO>> response = postService.searchPosts(keyword);
        return ResponseEntity.ok(response);
    }
}

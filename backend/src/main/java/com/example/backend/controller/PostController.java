package com.example.backend.controller;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.PostDTO;
import com.example.backend.dto.request.CreatePostRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    ApiResponse<?> createPost(@AuthenticationPrincipal PostAuthor postAuthorDTO , @RequestBody CreatePostRequest createPostRequest, HttpServletRequest request , HttpServletResponse response ){
        return postService.createPost(postAuthorDTO,createPostRequest) ;
    }

    //Post modification end points

    @PostMapping("/modifyPost")
    ApiResponse<PostDTO> modifyPost(@AuthenticationPrincipal PostAuthor postAuthorDTO, @RequestBody PostDTO postDTO, HttpServletRequest request , HttpServletResponse response ){
        return postService.updatePost(postAuthorDTO,postDTO) ;
    }

    // Currently i will keep the service methods using
    // PostDto but the end point will depend on postId
    @PostMapping("/{postId}/likes/add")
    ApiResponse<PostDTO> addLike(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId, HttpServletRequest request , HttpServletResponse response ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        return postService.addLike(postDTO) ;
    }

    @PostMapping("/{postId}/likes/remove")
    ApiResponse<PostDTO> removeLike(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId, HttpServletRequest request , HttpServletResponse response ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        return postService.removeLike(postDTO) ;
    }

    /**
     * Using query params for requests is not ideal but for
     * now we will use it until we have enough time
     */
    @PostMapping("/{postId}/comments/add")
    ApiResponse<PostDTO> addComment(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId , @RequestParam String comment ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        return postService.addComment(comment,postDTO) ;
    }

    @PostMapping("/{postId}/comments/remove")
    ApiResponse<PostDTO> removeComment(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId , @RequestParam String comment ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        return postService.removeComment(comment,postDTO) ;
    }

    @PostMapping("/{postId}/delete")
    ApiResponse<PostDTO> deletePost(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postId);
        return postService.deletePost(postAuthorDTO,postDTO) ;
    }

    /**
     * Post retrieval endPoints
     */

    @GetMapping("/{postId}/get")
    ApiResponse<PostDTO> getPost(@AuthenticationPrincipal PostAuthor postAuthorDTO, @PathVariable String postId ) {
        return postService.getPost(postId) ;
    }

    @GetMapping("/getAll")
    ApiResponse<List<PostDTO>> getAllPosts(@AuthenticationPrincipal PostAuthor postAuthorDTO, @RequestParam Integer page , @RequestParam Integer pageSize  ) {
        return postService.getAllPosts(page,pageSize) ;
    }

    @GetMapping("/getAuthor")
    ApiResponse<List<PostDTO>> getPostsByAuthor(@AuthenticationPrincipal PostAuthor postAuthorDTO, @RequestParam Integer page , @RequestParam Integer pageSize  ) {
        return postService.getPostsByAuthor(postAuthorDTO,page,pageSize) ;
    }

    @GetMapping("/getPostsStatus")
    ApiResponse<List<PostDTO>> getPostsByStatus(@AuthenticationPrincipal PostAuthor postAuthorDTO, @RequestParam String status  ) {
        return postService.getPostsByStatus(status) ;
    }

    @GetMapping("/search")
    ApiResponse<List<PostDTO>> searchPosts(@RequestParam String keyword, @AuthenticationPrincipal PostAuthor postAuthorDTO) {
        return postService.searchPosts(keyword) ;
    }



}

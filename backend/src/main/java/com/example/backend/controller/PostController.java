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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepo postRepo;

    @Mock
    private DoctorRepo doctorRepo;

    @Mock
    private SupervisorRepo supervisorRepo;

    @InjectMocks
    private PostService postService;

    private Doctor testDoctor;
    private Supervisor testSupervisor;
    private Post testPost;
    private DoctorDTO doctorDTO;
    private SupervisorDTO supervisorDTO;
    private CreatePostRequest createPostRequest;

    @BeforeEach
    void setUp() {
        // Setup test doctor
        testDoctor = new Doctor();
        testDoctor.setId(1L);
        testDoctor.setName("Dr. Test");

        // Setup test supervisor
        testSupervisor = new Supervisor();
        testSupervisor.setId(2L);
        testSupervisor.setName("Supervisor Test");

        // Setup test post
        testPost = new Post();
        testPost.setId(1L);
        testPost.setTitle("Test Post");
        testPost.setContent("Test Content");
        testPost.setStatus("ACTIVE");
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setLikeCount(0);
        testPost.setCommentCount(0);
        testPost.setTags("[]");
        Set<Doctor> doctors = new HashSet<>();
        doctors.add(testDoctor);
        testPost.setDoctors(doctors);

        // Setup DTOs
        doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId("1");

        supervisorDTO = new SupervisorDTO();
        supervisorDTO.setEmployeeId("2");

        // Setup create request
        createPostRequest = new CreatePostRequest();
        createPostRequest.setPostContent("New Post Content");
        createPostRequest.setPostType("ACTIVE");
        createPostRequest.setTags(new String[]{"tag1", "tag2"});
    }

    // ==================== CREATE POST TESTS ====================

    @Test
    void createPost_WithDoctor_Success() {
        // Arrange
        when(doctorRepo.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.createPost(doctorDTO, createPostRequest);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Created post successfully", response.getMessage());
        assertNotNull(response.getData());
        verify(doctorRepo).findById(1L);
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void createPost_WithSupervisor_Success() {
        // Arrange
        when(supervisorRepo.findById(2L)).thenReturn(Optional.of(testSupervisor));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.createPost(supervisorDTO, createPostRequest);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Created post successfully", response.getMessage());
        assertNotNull(response.getData());
        verify(supervisorRepo).findById(2L);
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void createPost_DoctorNotFound_ReturnsBadRequest() {
        // Arrange
        when(doctorRepo.findById(1L)).thenReturn(Optional.empty());

        // Act
        ApiResponse<PostDTO> response = postService.createPost(doctorDTO, createPostRequest);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Doctor not found", response.getMessage());
        verify(postRepo, never()).save(any(Post.class));
    }

    @Test
    void createPost_InvalidDoctorId_ReturnsBadRequest() {
        // Arrange
        doctorDTO.setDoctorId("invalid");

        // Act
        ApiResponse<PostDTO> response = postService.createPost(doctorDTO, createPostRequest);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Invalid doctor ID format", response.getMessage());
    }

    @Test
    void createPost_UnauthorizedUser_ReturnsUnauthorized() {
        // Arrange - Using a mock PostAuthor that's neither Doctor nor Supervisor
        class InvalidAuthor implements PostAuthor {}
        InvalidAuthor invalidAuthor = new InvalidAuthor();

        // Act
        ApiResponse<PostDTO> response = postService.createPost(invalidAuthor, createPostRequest);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("This user is not authorized", response.getMessage());
    }

    // ==================== UPDATE POST TESTS ====================

    @Test
    void updatePost_Success() {
        // Arrange
        PostDTO updateDTO = new PostDTO();
        updateDTO.setPostId("1");
        updateDTO.setTitle("Updated Title");
        updateDTO.setPostContent("Updated Content");
        updateDTO.setLikeCount(0);
        updateDTO.setTags(new String[]{"newTag"});
        updateDTO.setCommentCount(0);
        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.updatePost(doctorDTO, updateDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Updated post successfully", response.getMessage());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void updatePost_PostNotFound_ReturnsBadRequest() {
        // Arrange
        PostDTO updateDTO = new PostDTO();
        updateDTO.setPostId("999");

        when(postRepo.findById(999L)).thenReturn(Optional.empty());

        // Act
        ApiResponse<PostDTO> response = postService.updatePost(doctorDTO, updateDTO);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Post not found", response.getMessage());
    }

    @Test
    void updatePost_UnauthorizedUser_ReturnsServiceUnavailable() {
        // Arrange
        PostDTO updateDTO = new PostDTO();
        updateDTO.setPostId("1");

        DoctorDTO wrongDoctor = new DoctorDTO();
        wrongDoctor.setDoctorId("999");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // Act
        ApiResponse<PostDTO> response = postService.updatePost(wrongDoctor, updateDTO);

        // Assert
        assertFalse(response.isSuccess());
        assertTrue(response.getMessage().contains("doesn't match"));
        verify(postRepo, never()).save(any(Post.class));
    }

    // ==================== LIKE TESTS ====================

    @Test
    void addLike_Success() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.addLike(postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Added like successfully", response.getMessage());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void removeLike_Success() {
        // Arrange
        testPost.setLikeCount(5);
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.removeLike(postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Removed like successfully", response.getMessage());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void removeLike_PreventNegativeCount() {
        // Arrange
        testPost.setLikeCount(0);
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.removeLike(postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals(0, testPost.getLikeCount());
        verify(postRepo).save(any(Post.class));
    }

    // ==================== COMMENT TESTS ====================

    @Test
    void addComment_Success() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");
        String comment = "Test comment";

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // Act
        ApiResponse<PostDTO> response = postService.addComment(comment, postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
    }

    @Test
    void removeComment_Success() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");
        String comment = "Test comment";

        // SETUP THE REAL STATE: Add the comment to the post so it can be removed
        testPost.addComment(comment); // OR: testPost.setComments(new ArrayList<>(List.of(comment)));

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // REMOVED: when(testPost.removeComment(comment)).thenReturn(true);

        when(postRepo.save(any(Post.class))).thenReturn(testPost);

        // Act
        ApiResponse<PostDTO> response = postService.removeComment(comment, postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Removed comment successfully", response.getMessage());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void removeComment_NotFound_ReturnsBadRequest() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");
        String comment = "Nonexistent comment";

        // testPost does not contain this comment, so the real removeComment() returns false automatically
        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // REMOVED: when(testPost.removeComment(comment)).thenReturn(false);

        // Act
        ApiResponse<PostDTO> response = postService.removeComment(comment, postDTO);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Comment not found", response.getMessage());
        verify(postRepo, never()).save(any(Post.class));
    }

    // ==================== DELETE POST TESTS ====================

    @Test
    void deletePost_Success() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));
        doNothing().when(postRepo).delete(any(Post.class));

        // Act
        ApiResponse<PostDTO> response = postService.deletePost(doctorDTO, postDTO);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Deleted post successfully", response.getMessage());
        verify(postRepo).delete(testPost);
    }

    @Test
    void deletePost_Unauthorized_ReturnsServiceUnavailable() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId("1");

        DoctorDTO wrongDoctor = new DoctorDTO();
        wrongDoctor.setDoctorId("999");

        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // Act
        ApiResponse<PostDTO> response = postService.deletePost(wrongDoctor, postDTO);

        // Assert
        assertFalse(response.isSuccess());
        verify(postRepo, never()).delete(any(Post.class));
    }

    // ==================== RETRIEVAL TESTS ====================

    @Test
    void getPost_Success() {
        // Arrange
        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // Act
        ApiResponse<PostDTO> response = postService.getPost("1");

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("1", response.getData().getPostId());
    }

    @Test
    void getPost_NotFound_ReturnsBadRequest() {
        // Arrange
        when(postRepo.findById(999L)).thenReturn(Optional.empty());

        // Act
        ApiResponse<PostDTO> response = postService.getPost("999");

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Post not found", response.getMessage());
    }

    @Test
    void getAllPosts_Success() {
        // Arrange
        List<Post> posts = Arrays.asList(testPost);
        Page<Post> postPage = new PageImpl<>(posts);

        when(postRepo.findAll(any(Pageable.class))).thenReturn(postPage);

        // Act
        ApiResponse<List<PostDTO>> response = postService.getAllPosts(0, 10);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void getPostsByAuthor_WithDoctor_Success() {
        // Arrange
        List<Post> posts = Arrays.asList(testPost);
        when(postRepo.findByDoctors_Id(1L)).thenReturn(posts);

        // Act
        ApiResponse<List<PostDTO>> response = postService.getPostsByAuthor(doctorDTO, 0, 10);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void getPostsByAuthor_WithSupervisor_Success() {
        // Arrange
        List<Post> posts = Arrays.asList(testPost);
        when(postRepo.findByDoctors_Id(2L)).thenReturn(posts);

        // Act
        ApiResponse<List<PostDTO>> response = postService.getPostsByAuthor(supervisorDTO, 0, 10);

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
    }

    @Test
    void getPostsByStatus_Success() {
        // Arrange
        List<Post> posts = Arrays.asList(testPost);
        when(postRepo.findByStatus("ACTIVE")).thenReturn(posts);

        // Act
        ApiResponse<List<PostDTO>> response = postService.getPostsByStatus("ACTIVE");

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void searchPosts_Success() {
        // Arrange
        List<Post> posts = Arrays.asList(testPost);
        when(postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase("test", "test"))
                .thenReturn(posts);

        // Act
        ApiResponse<List<PostDTO>> response = postService.searchPosts("test");

        // Assert
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
    }

    @Test
    void searchPosts_EmptyKeyword_ReturnsBadRequest() {
        // Act
        ApiResponse<List<PostDTO>> response = postService.searchPosts("");

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Search keyword cannot be empty", response.getMessage());
    }

    @Test
    void searchPosts_NullKeyword_ReturnsBadRequest() {
        // Act
        ApiResponse<List<PostDTO>> response = postService.searchPosts(null);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Search keyword cannot be empty", response.getMessage());
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    void createPost_WithEmptyContent_UsesDefaultContent() {
        // Arrange
        createPostRequest.setPostContent("");
        when(doctorRepo.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(postRepo.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            assertEquals("No content", savedPost.getContent());
            return savedPost;
        });

        // Act
        ApiResponse<PostDTO> response = postService.createPost(doctorDTO, createPostRequest);

        // Assert
        assertTrue(response.isSuccess());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void createPost_WithNullTags_SetsEmptyArray() {
        // Arrange
        createPostRequest.setTags(null);
        when(doctorRepo.findById(1L)).thenReturn(Optional.of(testDoctor));
        when(postRepo.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            assertEquals("[]", savedPost.getTags());
            return savedPost;
        });

        // Act
        ApiResponse<PostDTO> response = postService.createPost(doctorDTO, createPostRequest);

        // Assert
        assertTrue(response.isSuccess());
        verify(postRepo).save(any(Post.class));
    }

    @Test
    void convertToDTO_WithSupervisor_SetsCorrectRole() {
        // Arrange
        Set<Doctor> doctors = new HashSet<>();
        doctors.add(testSupervisor);
        testPost.setDoctors(doctors);

        // This test verifies the private method through a public method
        when(postRepo.findById(1L)).thenReturn(Optional.of(testPost));

        // Act
        ApiResponse<PostDTO> response = postService.getPost("1");

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("SUPERVISOR", response.getData().getAuthorRole());
    }
}
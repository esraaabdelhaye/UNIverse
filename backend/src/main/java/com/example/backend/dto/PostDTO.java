package com.example.backend.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private String postId;
    private String authorId;
    private String authorName;
    private String authorRole;
    private String postContent;
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer commentCount;
    private String postType;
    private String title ;
    private String[] tags;

    public PostDTO() {}

    public PostDTO(String postId, String authorId, String authorName, String authorRole, String postContent,String title,
                   LocalDateTime createdDate, Integer likeCount, Integer commentCount, String postType, String[] tags) {
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorRole = authorRole;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.postType = postType;
        this.tags = tags;
        this.title = title;
    }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorRole() { return authorRole; }
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }

    public String getPostContent() { return postContent; }
    public void setPostContent(String postContent) { this.postContent = postContent; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }

    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
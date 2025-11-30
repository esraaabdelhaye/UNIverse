package com.example.backend.dto.request;

public class CreatePostRequest {
    private String postContent;
    private String postType;
    private String[] tags;
    private byte[] attachmentFile;
    private String visibility;

    public CreatePostRequest() {}

    public CreatePostRequest(String postContent, String postType, String[] tags,
                             byte[] attachmentFile, String visibility) {
        this.postContent = postContent;
        this.postType = postType;
        this.tags = tags;
        this.attachmentFile = attachmentFile;
        this.visibility = visibility;
    }

    public String getPostContent() { return postContent; }
    public void setPostContent(String postContent) { this.postContent = postContent; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public byte[] getAttachmentFile() { return attachmentFile; }
    public void setAttachmentFile(byte[] attachmentFile) { this.attachmentFile = attachmentFile; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
}


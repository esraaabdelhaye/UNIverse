export interface CreatePostRequest {
  postContent: string;
  postType: string;
  tags?: string[];
  attachmentFile?: File;
  visibility: string;
}

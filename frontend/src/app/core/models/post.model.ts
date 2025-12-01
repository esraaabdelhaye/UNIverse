export interface Post {
  postId: string;
  authorId: string;
  authorName: string;
  authorRole: string;
  postContent: string;
  createdDate: string;
  likeCount: number;
  commentCount: number;
  postType: 'ANNOUNCEMENT' | 'REGULAR_POST';
  tags?: string[];
}

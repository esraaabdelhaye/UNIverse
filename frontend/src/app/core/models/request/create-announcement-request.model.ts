export interface CreateAnnouncementRequest {
  courseCode: string;
  title: string;
  content: string;
  publishDate: string;
  visibility: string;
  attachments?: string[];
}

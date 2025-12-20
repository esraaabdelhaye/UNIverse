export interface createAnnouncementRequest {
  courseCode: string;
  title: string;
  content: string;

  publishDate: Date; // Changed from createdAt to match backend DTO
  status: string;
  visibility: string;
  attachments?: string[];
}

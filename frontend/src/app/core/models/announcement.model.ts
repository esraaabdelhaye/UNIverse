export interface Announcement {
  announcementId: string;
  courseCode: string;
  title: string;
  content: string;
  createdBy: string;
  createdDate: string;
  status: 'DRAFT' | 'SCHEDULED' | 'PUBLISHED' | 'AWAITING_APPROVAL';
  visibility: string;
  attachments?: string[];
}

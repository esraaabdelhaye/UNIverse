export interface Announcement {
    announcementId: number;
    courseCode: string;
    title: string;
    content: string;
    createdBy: string;
    createdDate: string;  // Changed from createdAt to match backend DTO
    status: string;
    visibility: string;
}
export interface CreateAssignmentRequest {
  courseCode: string;
  assignmentTitle: string;
  description: string;
  dueDate: string;
  attachments?: string[];
}

export interface Assignment {
  assignmentId: string;
  courseCode: string;
  assignmentTitle: string;
  description: string;
  dueDate: string;
  status: 'PENDING' | 'SUBMITTED' | 'GRADED' | 'PAST_DUE';
  grade?: number;
  feedbackUrl?: string;
  attachments?: string[];
}

export interface AssignmentSubmission {
  id: string;
  assignmentId: string;
  studentId: string;
  submissionDate: Date;
  fileName: string;
  filePath: string;
  status: 'submitted' | 'late' | 'missing';
  grade?: number;
  feedback?: string;
  gradedBy?: string;
  gradedAt?: Date;
}

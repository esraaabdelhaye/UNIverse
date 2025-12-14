export interface Submission {
  id: number;
  studentId: number;
  assignmentId: number;
  courseId: number;
  submissionDate: string;
  status: 'submitted' | 'graded' | 'pending';
  grade?: string;
  feedback?: string;
  submissionFile?: string;
}

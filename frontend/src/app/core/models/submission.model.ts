export interface Submission {
  id: number;
  studentId: number;
  assignmentId: string;
  courseId: number;
  studentName?: string;
  // studentAvatar?: string;
  submissionDate: number;
  status: 'submitted' | 'graded' | 'grading';
  grade?: string;
  feedback?: string;
  submissionFile?: string;
}

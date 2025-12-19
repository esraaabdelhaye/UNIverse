export interface Submission {
  submissionId: number;
  studentId: number;
  assignmentId: string;
  courseId: number;
  studentName?: string;
  // studentAvatar?: string;
  assignmentTitle?: string;
  submissionDate: number;
  status: 'submitted' | 'graded' | 'grading';
  grade?: string;
  feedback?: string;
  submissionFile?: string;
}

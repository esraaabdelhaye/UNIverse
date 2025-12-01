export interface Submission {
  submissionId: string;
  studentId: string;
  studentName: string;
  assignmentTitle: string;
  submissionDate: string;
  status: 'SUBMITTED' | 'GRADED' | 'LATE';
  grade?: number;
}

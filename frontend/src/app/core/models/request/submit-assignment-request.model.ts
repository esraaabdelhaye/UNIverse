export interface SubmitAssignmentRequest {
  assignmentId: string;
  studentId: string;
  submissionContent: string;
  submissionFile?: File;
}

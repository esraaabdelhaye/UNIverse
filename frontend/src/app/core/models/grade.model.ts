export interface Grade {
  id: number;
  studentId: number;
  courseCode: string;
  courseName: string;
  assignmentId: number;
  assignmentName: string;
  score: number;
  feedback?: string;
  gradingStatus: string;
  gradedDate: string;
}

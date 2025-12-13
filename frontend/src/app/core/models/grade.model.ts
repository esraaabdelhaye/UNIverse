export interface Grade {
  gradeId?: string;
  id?: number;
  studentId: number;
  courseCode: string;
  courseName?: string;
  courseTitle?: string;
  assignmentId?: number;
  assignmentName?: string;
  score: number;
  feedback?: string;
  gradingStatus: string;
  gradedDate?: string;
  status?: string;
}

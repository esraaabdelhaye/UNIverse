export interface StudentGrade {
  id: string;
  studentId: string;
  courseId: string;
  assignmentId: string;
  score: number;
  percentage: number;
  letterGrade: string;
}

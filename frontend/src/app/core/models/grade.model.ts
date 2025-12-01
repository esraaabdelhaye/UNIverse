export interface Grade {
  gradeId: string;
  studentId: string;
  studentName: string;
  courseCode: string;
  courseTitle: string;
  score: number;
  letterGrade: string;
  gradingStatus: 'PENDING' | 'GRADED';
  feedback?: string;
  gradedDate?: string;
  gradedBy?: string;
}

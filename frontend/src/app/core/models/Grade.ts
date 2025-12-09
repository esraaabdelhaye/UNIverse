export interface Grade {
  id: string;
  submissionId: string;
  courseId: string;
  score: number;
  totalPoints: number;
  feedback: string;
  gradedBy: string;
  gradedAt: Date;
}

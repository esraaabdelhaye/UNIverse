export interface Assignment {
  id: number;
  title: string;
  description: string;
  dueDate: string;
  course: string;
  courseId: number;
  maxScore: number;
  status?: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
}

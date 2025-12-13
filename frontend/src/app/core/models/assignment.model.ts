export interface Assignment {
  id: number;
  assignmentId?: string;
  title: string;
  assignmentTitle?: string;
  description: string;
  dueDate: string;
  course: string;
  courseCode?: string;
  courseId: number;
  maxScore?: number;
  status?: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
}

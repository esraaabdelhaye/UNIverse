export interface Assignment {
  id: string;
  courseId: string;
  title: string;
  description: string;
  dueDate: Date;
  totalPoints: number;
  fileResource?: string;
  createdBy: string;
  createdAt: Date;
  updatedAt: Date;
}

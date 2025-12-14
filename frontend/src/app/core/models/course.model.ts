export interface Course {
  id: number;
  courseId?: string;
  courseCode: string;
  courseTitle: string;
  credits: number;
  semester: string;
  description: string;
  department?: string;
  professor?: string;
  progress?: number;
}

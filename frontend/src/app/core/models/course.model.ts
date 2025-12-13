export interface Course {
  id: number;
  courseCode: string;
  courseTitle: string;
  credits: number;
  semester: string;
  description: string;
  department?: string;
  professor?: string;
  progress?: number;
}

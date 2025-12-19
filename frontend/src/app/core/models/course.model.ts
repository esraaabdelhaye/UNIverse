export interface Course {
  id: number;
  courseId?: string;
  courseCode: string;
  capacity: number;
  courseTitle: string;
  credits: number;
  semester: string;
  description: string;
  department?: string;
  instructorName?: string;
  progress?: number;
}

export interface CreateCourseRequest {
  courseCode: string;
  courseTitle: string;
  department: string;
  instructorId: string;
  capacity: number;
  semester: string;
}

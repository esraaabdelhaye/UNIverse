export interface CourseEnrollment {
  id: string;
  studentId: string;
  courseId: string;
  enrolledAt: Date;
  grade?: number;
}

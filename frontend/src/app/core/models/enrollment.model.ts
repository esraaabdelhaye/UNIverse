import { Course } from './course.model';
import { Student } from './student.model';

export interface CourseEnrollment {
  studentId: number;
  courseId: number;
  enrollmentDate: string;
  grade?: string;
  course?: Course;
  student?: Student;
}

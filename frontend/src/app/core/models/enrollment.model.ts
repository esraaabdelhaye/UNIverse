import {Course} from './course.model';

export interface CourseEnrollment {
  id: number;
  studentId: number;
  courseId: number;
  enrollmentDate: string;
  grade?: string;
  course?: Course;
}

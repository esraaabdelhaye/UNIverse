import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from './api.service';
import { Student } from '../models/student.model';
import { Course } from '../models/course.model';
import { Assignment } from '../models/assignment.model';
import { Grade } from '../models/grade.model';
import { ApiResponse, PaginatedResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  constructor(private api: ApiService) {}

  getStudentById(id: number): Observable<ApiResponse<Student>> {
    return this.api.get<ApiResponse<Student>>(`/students/${id}`);
  }

  getStudentByEmail(email: string): Observable<ApiResponse<Student>> {
    return this.api.get<ApiResponse<Student>>(`/students/email/${email}`);
  }

  getAllStudents(page: number = 0, size: number = 10): Observable<ApiResponse<PaginatedResponse<Student>>> {
    return this.api.get<ApiResponse<PaginatedResponse<Student>>>(`/students?page=${page}&size=${size}`);
  }

  updateStudent(id: number, student: Student): Observable<ApiResponse<Student>> {
    return this.api.put<ApiResponse<Student>>(`/students/${id}`, student);
  }

  deleteStudent(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/students/${id}`);
  }

  getStudentCourses(studentId: number): Observable<{
    statusCode: number;
    message: string;
    data: {
      id: number;
      courseId: number | undefined;
      courseCode: string;
      courseTitle: string;
      credits: number;
      semester: string;
      description: string;
      department?: string;
      professor?: string;
      progress?: number
    }[] | undefined;
    success: boolean
  }> {
    return this.api.get<ApiResponse<Course[]>>(`/courses`).pipe(
      map(response => ({
        ...response,
        data: Array.isArray(response.data)
          ? response.data.map(course => ({
              ...course,
              courseId: course.courseId ? parseInt(String(course.courseId), 10) : undefined
            }))
          : response.data
      }))
    );
  }

  getStudentAssignments(studentId: number): Observable<ApiResponse<Assignment[]>> {
    return this.api.get<ApiResponse<Assignment[]>>(`/api/assignments`);
  }

  getStudentGrades(studentId: number): Observable<ApiResponse<Grade[]>> {
    return this.api.get<ApiResponse<Grade[]>>(`/students/${studentId}/grades`);
  }

  enrollInCourse(studentId: number, courseId: number): Observable<ApiResponse<any>> {
    return this.api.post<ApiResponse<any>>(`/students/${studentId}/enroll/${courseId}`, {});
  }

  unenrollFromCourse(studentId: number, courseId: number): Observable<ApiResponse<any>> {
    return this.api.post<ApiResponse<any>>(`/students/${studentId}/unenroll/${courseId}`, {});
  }
}

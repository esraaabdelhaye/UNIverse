import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from './api.service';
import { Course } from '../models/course.model';
import { ApiResponse, PaginatedResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  constructor(private api: ApiService) { }

  getAllCourses(page: number = 0, size: number = 10): Observable<ApiResponse<PaginatedResponse<Course>>> {
    return this.api.get<ApiResponse<PaginatedResponse<Course>>>(`/api/courses?page=${page}&size=${size}`, { withCredentials: true }).pipe(
    );
  }

  getCourseById(id: number): Observable<ApiResponse<Course>> {
    return this.api.get<ApiResponse<Course>>(`/api/courses/${id}`, { withCredentials: true }).pipe(
    );
  }

  getCourseByCode(code: string): Observable<ApiResponse<Course>> {
    return this.api.get<ApiResponse<Course>>(`/api/courses/code/${code}`, { withCredentials: true }).pipe(
    );
  }

  getCoursesByDepartment(departmentId: number): Observable<ApiResponse<Course[]>> {
    return this.api.get<ApiResponse<Course[]>>(`/api/courses/department/${departmentId}`, { withCredentials: true }).pipe(
    );
  }

  getCoursesBySemester(semester: string): Observable<ApiResponse<Course[]>> {
    return this.api.get<ApiResponse<Course[]>>(`/api/courses/semester/${semester}`, { withCredentials: true }).pipe(
    );
  }

  enrollInCourse(studentId: number, courseId: number): Observable<ApiResponse<any>> {
    return this.api.post<ApiResponse<any>>(`/api/courses/${courseId}/enroll`, { studentId }, { withCredentials: true });
  }

  createCourse(course: any): Observable<ApiResponse<Course>> {
    return this.api.post<ApiResponse<Course>>('/api/courses', course);
  }

  updateCourse(id: number, course: any): Observable<ApiResponse<Course>> {
    return this.api.put<ApiResponse<Course>>(`/api/courses/${id}`, course);
  }

  deleteCourse(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/api/courses/${id}`);
  }

  updateCourseStatus(id: number, status: string): Observable<ApiResponse<Course>> {
    return this.api.patch<ApiResponse<Course>>(`/api/courses/${id}/status?status=${status}`, {});
  }
}

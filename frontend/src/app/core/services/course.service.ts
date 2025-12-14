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
  constructor(private api: ApiService) {}

  getAllCourses(page: number = 0, size: number = 10): Observable<ApiResponse<PaginatedResponse<Course>>> {
    return this.api.get<ApiResponse<PaginatedResponse<Course>>>(`/courses?page=${page}&size=${size}`,{withCredentials: true}).pipe(
    );
  }

  getCourseById(id: number): Observable<ApiResponse<Course>> {
    return this.api.get<ApiResponse<Course>>(`/courses/${id}`,{withCredentials: true}).pipe(
    );
  }

  getCourseByCode(code: string): Observable<ApiResponse<Course>> {
    return this.api.get<ApiResponse<Course>>(`/courses/code/${code}`,{withCredentials: true}).pipe(
    );
  }

  getCoursesByDepartment(departmentId: number): Observable<ApiResponse<Course[]>> {
    return this.api.get<ApiResponse<Course[]>>(`/courses/department/${departmentId}`,{withCredentials: true}).pipe(
    );
  }

  getCoursesBySemester(semester: string): Observable<ApiResponse<Course[]>> {
    return this.api.get<ApiResponse<Course[]>>(`/courses/semester/${semester}`,{withCredentials: true}).pipe(
    );
  }

  enrollInCourse(studentId: number, courseId: number): Observable<ApiResponse<any>> {
    return this.api.post<ApiResponse<any>>(`/courses/${courseId}/enroll`, { studentId },{withCredentials: true});
  }
}

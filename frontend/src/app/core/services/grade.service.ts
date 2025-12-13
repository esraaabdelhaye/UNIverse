import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Grade } from '../models/grade.model';
import {ApiResponse} from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  constructor(private api: ApiService) {}

  getStudentGrades(studentId: number): Observable<ApiResponse<Grade[]>> {
    return this.api.get<ApiResponse<Grade[]>>(`/grades/student/${studentId}`);
  }

  getCourseGrades(studentId: number, courseId: number): Observable<ApiResponse<Grade[]>> {
    return this.api.get<ApiResponse<Grade[]>>(`/grades/student/${studentId}/course/${courseId}`);
  }

  getAverageGrade(studentId: number): Observable<ApiResponse<number>> {
    return this.api.get<ApiResponse<number>>(`/grades/student/${studentId}/average`);
  }

  getCourseAverageGrade(studentId: number, courseId: number): Observable<ApiResponse<number>> {
    return this.api.get<ApiResponse<number>>(`/grades/student/${studentId}/course/${courseId}/average`);
  }

  getGradesByStatus(studentId: number, status: string): Observable<ApiResponse<Grade[]>> {
    return this.api.get<ApiResponse<Grade[]>>(`/grades/student/${studentId}/status/${status}`);
  }

  updateGrade(submissionId: number, grade: string, feedback?: string): Observable<ApiResponse<Grade>> {
    const params = feedback ? `?grade=${grade}&feedback=${feedback}` : `?grade=${grade}`;
    return this.api.put<ApiResponse<Grade>>(`/grades/submission/${submissionId}${params}`, {});
  }
}

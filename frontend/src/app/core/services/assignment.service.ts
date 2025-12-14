import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Assignment } from '../models/assignment.model';
import {ApiResponse} from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {
  constructor(private api: ApiService) {}

  getAssignmentById(id: number): Observable<ApiResponse<Assignment>> {
    return this.api.get<ApiResponse<Assignment>>(`/api/assignments/${id}`);
  }

  getAssignmentsByCourse(courseId: number): Observable<ApiResponse<Assignment[]>> {
    return this.api.get<ApiResponse<Assignment[]>>(`/api/assignments/course/${courseId}`);
  }

  getAllAssignments(): Observable<ApiResponse<Assignment[]>> {
    return this.api.get<ApiResponse<Assignment[]>>(`/api/assignments`);
  }

  getSubmissionCount(assignmentId: number): Observable<ApiResponse<number>> {
    return this.api.get<ApiResponse<number>>(`/api/assignments/${assignmentId}/submissions/count`);
  }

  createAssignment(courseId: number, assignment: Assignment): Observable<ApiResponse<Assignment>> {
    return this.api.post<ApiResponse<Assignment>>(`/api/assignments/course/${courseId}`, assignment);
  }

  updateAssignment(id: number, assignment: Assignment): Observable<ApiResponse<Assignment>> {
    return this.api.put<ApiResponse<Assignment>>(`/api/assignments/${id}`, assignment);
  }

  deleteAssignment(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/api/assignments/${id}`);
  }
}

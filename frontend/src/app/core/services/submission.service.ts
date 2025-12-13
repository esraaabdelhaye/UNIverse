import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Submission } from '../models/submission.model';
import {ApiResponse} from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {
  constructor(private api: ApiService) {}

  submitAssignment(studentId: number, assignmentId: number, submissionFile: string): Observable<ApiResponse<Submission>> {
    return this.api.post<ApiResponse<Submission>>(
      `/submissions/student/${studentId}/assignment/${assignmentId}`,
      { submissionFile }
    );
  }

  getSubmission(submissionId: number): Observable<ApiResponse<Submission>> {
    return this.api.get<ApiResponse<Submission>>(`/submissions/${submissionId}`);
  }

  getSubmissionStatus(studentId: number, assignmentId: number): Observable<ApiResponse<any>> {
    return this.api.get<ApiResponse<any>>(
      `/submissions/student/${studentId}/assignment/${assignmentId}/status`
    );
  }

  getStudentSubmissions(studentId: number): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/submissions/student/${studentId}`);
  }

  getAssignmentSubmissions(assignmentId: number): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/submissions/assignment/${assignmentId}`);
  }

  updateSubmission(submissionId: number, submissionFile: string): Observable<ApiResponse<Submission>> {
    return this.api.put<ApiResponse<Submission>>(`/submissions/${submissionId}`, { submissionFile });
  }

  getSubmissionsByStatus(status: string): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/submissions/status/${status}`);
  }
}

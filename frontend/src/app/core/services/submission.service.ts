import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Submission } from '../models/submission.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root',
})
export class SubmissionService {
  constructor(private api: ApiService) {}

  submitAssignment(
    studentId: number,
    assignmentId: number,
    submissionFile: string
  ): Observable<ApiResponse<Submission>> {
    return this.api.post<ApiResponse<Submission>>(
      `/api/submissions/student/${studentId}/assignment/${assignmentId}`,
      { submissionFile }
    );
  }

  getSubmission(submissionId: number): Observable<ApiResponse<Submission>> {
    return this.api.get<ApiResponse<Submission>>(`/api/submissions/${submissionId}`);
  }

  getSubmissionStatus(studentId: number, assignmentId: number): Observable<ApiResponse<any>> {
    return this.api.get<ApiResponse<any>>(
      `/api/submissions/student/${studentId}/assignment/${assignmentId}/status`
    );
  }

  getStudentSubmissions(studentId: number): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/api/submissions/student/${studentId}`);
  }

  getAssignmentSubmissions(assignmentId: number): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/api/submissions/assignment/${assignmentId}`);
  }

  updateSubmission(
    submissionId: number,
    submissionFile: string
  ): Observable<ApiResponse<Submission>> {
    return this.api.put<ApiResponse<Submission>>(`/api/submissions/${submissionId}`, {
      submissionFile,
    });
  }

  getSubmissionsByStatus(status: string): Observable<ApiResponse<Submission[]>> {
    return this.api.get<ApiResponse<Submission[]>>(`/api/submissions/status/${status}`);
  }

  updateSubmissionStatus(
    submissionId: number,
    status: string
  ): Observable<ApiResponse<Submission>> {
    return this.api.put<ApiResponse<Submission>>(`/api/submissions/status/${submissionId}?status=${status}`, {});
  }
  updateSubmissionGrade(
    submissionId: number,
    status: string,
    grade: string
  ): Observable<ApiResponse<Submission>> {
    return this.api.put<ApiResponse<Submission>>(`/api/submissions/grade/${submissionId}?status=${status}&grade=${grade}`, {});
  }
}

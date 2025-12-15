import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ApiResponse, PaginatedResponse } from '../models/api-response.model';

export interface SupervisorDTO {
  employeeId: string;
  fullName: string;
  email: string;
  department: string;
  positionTitle: string;
  officeLocation: string;
  role: string;
}

export interface PerformanceMetrics {
  avgStudentFeedback: number;
  courseSuccessRate: number;
  publicationsCount: number;
  timetableGenerationTime: string;
  courseApprovalRate: number;
  resourceConflictPercentage: number;
  systemUptimePercentage: number;
  totalStudents?: number;
  totalFaculty?: number;
  activeCourses: number;
  pendingApprovals: number;
  totalDepartments?: number;
  systemAlerts: string[];
}

export interface DashboardAlert {
  type: string;
  title: string;
  text: string;
  actionText: string;
}

export interface DashboardStats {
  totalUsers: number;
  totalFaculty: number;
  totalStudents: number;
  activeCourses: number;
  pendingApprovals: number;
  totalCourses: number;
  alerts: DashboardAlert[];
}

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {
  constructor(private api: ApiService) {}

  getAllSupervisors(page: number = 0, size: number = 10): Observable<ApiResponse<PaginatedResponse<SupervisorDTO>>> {
    return this.api.get<ApiResponse<PaginatedResponse<SupervisorDTO>>>(`/supervisors?page=${page}&size=${size}`, {withCredentials: true});
  }

  getSupervisorById(id: number): Observable<ApiResponse<SupervisorDTO>> {
    return this.api.get<ApiResponse<SupervisorDTO>>(`/supervisors/${id}`, {withCredentials: true});
  }

  getSupervisorByEmail(email: string): Observable<ApiResponse<SupervisorDTO>> {
    return this.api.get<ApiResponse<SupervisorDTO>>(`/supervisors/email/${email}`, {withCredentials: true});
  }

  createSupervisor(supervisor: any): Observable<ApiResponse<SupervisorDTO>> {
    return this.api.post<ApiResponse<SupervisorDTO>>('/supervisors', supervisor, {withCredentials: true});
  }

  updateSupervisor(id: number, supervisor: any): Observable<ApiResponse<SupervisorDTO>> {
    return this.api.put<ApiResponse<SupervisorDTO>>(`/supervisors/${id}`, supervisor);
  }

  deleteSupervisor(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/supervisors/${id}`);
  }

  getCoordinatedCourses(supervisorId: number): Observable<ApiResponse<any[]>> {
    return this.api.get<ApiResponse<any[]>>(`/supervisors/${supervisorId}/coordinated-courses`, {withCredentials: true});
  }

  assignCourseToCoordinator(supervisorId: number, courseId: number): Observable<ApiResponse<string>> {
    return this.api.post<ApiResponse<string>>(`/supervisors/${supervisorId}/courses/${courseId}`, {}, {withCredentials: true});
  }

  updateCourseStatus(courseId: number, status: string): Observable<ApiResponse<any>> {
    return this.api.patch<ApiResponse<any>>(`/supervisors/courses/${courseId}/status?status=${status}`, {});
  }

  getSystemPerformance(): Observable<ApiResponse<PerformanceMetrics>> {
    return this.api.get<ApiResponse<PerformanceMetrics>>('/supervisors/performance/metrics', {withCredentials: true});
  }

  getDashboardStats(): Observable<ApiResponse<DashboardStats>> {
    return this.api.get<ApiResponse<DashboardStats>>('/supervisors/stats', {withCredentials: true});
  }
}

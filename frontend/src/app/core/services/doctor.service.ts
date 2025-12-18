import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ApiResponse, PaginatedResponse } from '../models/api-response.model';
import { Assignment } from '../models/assignment.model';

export interface FacultyItem {
  doctorId: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  specialization: string;
  department: string;
  officeLocation: string;
  qualifications: string;
  availableForConsultation: boolean;
  courseCount: number;
  active: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  constructor(private api: ApiService) {}

  getAllDoctors(
    page: number = 0,
    size: number = 10
  ): Observable<ApiResponse<PaginatedResponse<FacultyItem>>> {
    return this.api.get<ApiResponse<PaginatedResponse<FacultyItem>>>(
      `/doctors?page=${page}&size=${size}`,
      { withCredentials: true }
    );
  }

  getDoctorById(id: number): Observable<ApiResponse<FacultyItem>> {
    return this.api.get<ApiResponse<FacultyItem>>(`/doctors/${id}`, { withCredentials: true });
  }

  getDoctorByEmail(email: string): Observable<ApiResponse<FacultyItem>> {
    return this.api.get<ApiResponse<FacultyItem>>(`/doctors/email/${email}`, {
      withCredentials: true,
    });
  }

  createDoctor(doctor: any): Observable<ApiResponse<FacultyItem>> {
    return this.api.post<ApiResponse<FacultyItem>>('/doctors', doctor, { withCredentials: true });
  }

  updateDoctor(id: number, doctor: any): Observable<ApiResponse<FacultyItem>> {
    return this.api.put<ApiResponse<FacultyItem>>(`/doctors/${id}`, doctor);
  }

  deleteDoctor(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/doctors/${id}`);
  }

  getDoctorCourses(doctorId: number): Observable<ApiResponse<any[]>> {
    return this.api.get<ApiResponse<any[]>>(`/doctors/${doctorId}/courses`, {
      withCredentials: true,
    });
  }

  assignCourseToDoctor(doctorId: number, courseId: number): Observable<ApiResponse<string>> {
    return this.api.post<ApiResponse<string>>(
      `/doctors/${doctorId}/courses/${courseId}`,
      {},
      { withCredentials: true }
    );
  }
}

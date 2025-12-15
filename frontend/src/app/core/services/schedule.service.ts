import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { environment } from '../../../environments/environment';

export interface Schedule {
  id: number;
  course: any; // Simplified for now
  room: string;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  semester: string;
}

export interface TimetableRequest {
  courseIds: number[];
  rooms: string[];
  days: string[];
  timeSlots: string[];
  semester: string;
  academicYear: string;
}

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private http = inject(HttpClient);
  // Assuming environment.apiUrl exists, otherwise hardcoding for now as mocking environment usually happens
  private apiUrl = 'http://localhost:8080/api/schedule';

  generateTimetable(request: TimetableRequest): Observable<Schedule[]> {
    return this.http.post<Schedule[]>(`${this.apiUrl}/generate`, request, { withCredentials: true });
  }

  getSchedule(semester: string, academicYear?: string): Observable<Schedule[]> {
    let url = `${this.apiUrl}?semester=${semester}`;
    if (academicYear) {
      url += `&academicYear=${academicYear}`;
    }
    return this.http.get<Schedule[]>(url, { withCredentials: true });
  }

  addManualSlot(schedule: any): Observable<Schedule> {
    return this.http.post<Schedule>(`${this.apiUrl}/manual`, schedule, { withCredentials: true });
  }

  updateManualSlot(id: number, schedule: any): Observable<Schedule> {
    return this.http.put<Schedule>(`${this.apiUrl}/manual/${id}`, schedule, { withCredentials: true });
  }

  deleteSlot(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }
}

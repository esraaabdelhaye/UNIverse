import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ApiResponse } from '../models/api-response.model';
import { Announcement } from '../models/announcment.model';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {
  constructor(private api: ApiService) {}

  getAnnouncementsByCoures(course: any): Observable<ApiResponse<Announcement[]>> {
    // Handle both course object and string
    const courseCode = typeof course === 'string'
      ? course
      : (course?.courseCode || course?.code);

    if (!courseCode) {
      throw new Error('Course code is required');
    }

    return this.api.get<ApiResponse<Announcement[]>>(
      `/announcement/get/course?courseCode=${courseCode}`
    );
  }

  getAllAnnouncements(): Observable<ApiResponse<Announcement[]>> {
    return this.api.get<ApiResponse<Announcement[]>>('/announcement/get/all?page=0&pageSize=100');
  }

  getAnnouncementById(announcementId: string): Observable<ApiResponse<Announcement>> {
    return this.api.get<ApiResponse<Announcement>>(
      `/announcement/get/${announcementId}`
    );
  }

  getAnnouncementsByStatus(status: string): Observable<ApiResponse<Announcement[]>> {
    return this.api.get<ApiResponse<Announcement[]>>(
      `/announcement/get/status?status=${status}`
    );
  }

  searchAnnouncements(keyword: string): Observable<ApiResponse<Announcement[]>> {
    return this.api.get<ApiResponse<Announcement[]>>(
      `/announcement/search?keyword=${keyword}`
    );
  }
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import {ApiResponse} from '../models/api-response.model';
import {Announcement} from '../models/announcment.model';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {
    constructor(private api: ApiService) {}

    getAnnouncementsByCoures(course: Course): Observable<ApiResponse<Announcement[]>> {
        return this.api.get<ApiResponse<Announcement[]>>(`/announcement/get/course?courseCode=${course.courseCode}`);
    }
}

import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-course-announcements',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './course-announcements.html',
  styleUrl: './course-announcements.css',
})
export class CourseAnnouncements {

}

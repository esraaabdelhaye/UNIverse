import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-course-availability',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './course-availability.html',
  styleUrl: './course-availability.css',
})
export class CourseAvailability {

}

import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-my-courses',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './my-courses.html',
  styleUrl: './my-courses.css',
})
export class MyCourses {

}

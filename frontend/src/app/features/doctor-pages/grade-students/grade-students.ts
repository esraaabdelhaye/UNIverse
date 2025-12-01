import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-grade-students',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './grade-students.html',
  styleUrl: './grade-students.css',
})
export class GradeStudents {

}

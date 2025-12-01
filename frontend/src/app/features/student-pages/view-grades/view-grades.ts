import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-view-grades',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-grades.html',
  styleUrl: './view-grades.css',
})
export class ViewGrades {

}

import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-add-grades',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './add-grades.html',
  styleUrl: './add-grades.css',
})
export class AddGrades {

}

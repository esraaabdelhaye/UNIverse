import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-studentrep-dashboard',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './studentrep-dashboard.html',
  styleUrl: './studentrep-dashboard.css',
})
export class StudentrepDashboard {

}

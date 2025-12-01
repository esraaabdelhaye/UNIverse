import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-ta-dashboard',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './ta-dashboard.html',
  styleUrl: './ta-dashboard.css',
})
export class TaDashboard {

}

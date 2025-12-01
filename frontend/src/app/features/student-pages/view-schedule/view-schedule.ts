import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-view-schedule',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-schedule.html',
  styleUrl: './view-schedule.css',
})
export class ViewSchedule {

}

import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule {

}

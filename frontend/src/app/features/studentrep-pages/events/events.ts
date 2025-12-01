import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './events.html',
  styleUrl: './events.css',
})
export class Events {

}

import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-live-feed',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './live-feed.html',
  styleUrl: './live-feed.css',
})
export class LiveFeed {

}

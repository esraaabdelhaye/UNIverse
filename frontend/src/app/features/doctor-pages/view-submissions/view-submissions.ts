import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-view-submissions',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-submissions.html',
  styleUrl: './view-submissions.css',
})
export class ViewSubmissions {

}

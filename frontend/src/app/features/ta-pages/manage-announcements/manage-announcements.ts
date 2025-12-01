import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-manage-announcements',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './manage-announcements.html',
  styleUrl: './manage-announcements.css',
})
export class ManageAnnouncements {

}

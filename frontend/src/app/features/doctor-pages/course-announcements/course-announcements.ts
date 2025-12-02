import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-course-announcements',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './course-announcements.html',
  styleUrl: './course-announcements.css',
})
export class CourseAnnouncements {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

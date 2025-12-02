import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-course-availability',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './course-availability.html',
  styleUrl: './course-availability.css',
})
export class CourseAvailability {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

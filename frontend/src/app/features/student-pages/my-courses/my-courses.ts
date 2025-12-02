import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-my-courses',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './my-courses.html',
  styleUrl: './my-courses.css',
})
export class MyCourses {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

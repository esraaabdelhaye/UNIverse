import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-review-performance',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './review-performance.html',
  styleUrl: './review-performance.css',
})
export class ReviewPerformance {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

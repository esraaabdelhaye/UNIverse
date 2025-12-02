import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-live-feed',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './live-feed.html',
  styleUrl: './live-feed.css',
})
export class LiveFeed {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-view-submissions',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-submissions.html',
  styleUrl: './view-submissions.css',
})
export class ViewSubmissions {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

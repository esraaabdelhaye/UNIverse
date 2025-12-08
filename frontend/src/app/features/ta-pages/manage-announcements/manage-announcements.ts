import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-manage-announcements',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './manage-announcements.html',
  styleUrl: './manage-announcements.css',
})
export class ManageAnnouncements {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

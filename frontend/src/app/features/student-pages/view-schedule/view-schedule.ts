import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-view-schedule',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-schedule.html',
  styleUrl: './view-schedule.css',
})
export class ViewSchedule {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

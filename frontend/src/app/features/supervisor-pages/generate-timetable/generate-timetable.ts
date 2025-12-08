import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-generate-timetable',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './generate-timetable.html',
  styleUrl: './generate-timetable.css',
})
export class GenerateTimetable {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

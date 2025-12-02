import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-view-grades',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './view-grades.html',
  styleUrl: './view-grades.css',
})
export class ViewGrades {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

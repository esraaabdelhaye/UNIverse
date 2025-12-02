import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-submit-assignments',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './submit-assignments.html',
  styleUrl: './submit-assignments.css',
})
export class SubmitAssignments {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

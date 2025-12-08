import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-add-grades',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './add-grades.html',
  styleUrl: './add-grades.css',
})
export class AddGrades {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

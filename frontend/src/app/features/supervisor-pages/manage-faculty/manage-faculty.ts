import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-manage-faculty',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './manage-faculty.html',
  styleUrl: './manage-faculty.css',
})
export class ManageFaculty {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

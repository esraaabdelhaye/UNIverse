import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-manage-student-grps',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './manage-student-grps.html',
  styleUrl: './manage-student-grps.css',
})
export class ManageStudentGrps {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
